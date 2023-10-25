package com.example.career.domain.user.Service;

import com.example.career.domain.consult.Repository.ConsultRepository;
import com.example.career.domain.user.Dto.*;
import com.example.career.domain.user.Entity.*;
import com.example.career.domain.user.Exception.DuplicateMemberException;
import com.example.career.domain.user.Exception.NotFoundMemberException;
import com.example.career.domain.user.Exception.PasswordWrongException;
import com.example.career.domain.user.Exception.UsernameWrongException;
import com.example.career.domain.user.Repository.*;
import com.example.career.domain.user.Util.SecurityUtil;
import com.example.career.global.utils.S3Uploader;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final TutorDetailRepository tutorDetailRepository;
    private final SchoolRepository schoolRepository;
    private final TagRepository tagRepository;
    private final CareerRepository careerRepository;
    private final PasswordEncoder passwordEncoder;
    private final ConsultRepository consultRepository;
    private final S3Uploader s3Uploader;
    @Override
    public User signIn(UserReqDto userReqDto) {
        String username = userReqDto.getUsername();
        String passwd = userReqDto.getPassword();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameWrongException(username));

        if (!passwordEncoder.matches(passwd, user.getPassword())) {
            throw new PasswordWrongException();
        }
        return user;
    }
    //    @Override
//    public User signUp(SignUpReqDto signUpReqDto) {
//        String passwd = signUpReqDto.getPassword();
//        signUpReqDto.setPassword(passwordEncoder.encode(passwd));
//        User user = signUpReqDto.toUserEntity();
//        return userRepository.save(user);
//    }



    @Transactional
    @Override
    public SignUpReqDto signup(SignUpReqDto signUpReqDto) {
        if (userRepository.findOneWithAuthoritiesByUsername(signUpReqDto.getUsername()).orElse(null) != null) {
            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다.");
        }
        signUpReqDto.setPassword(passwordEncoder.encode(signUpReqDto.getPassword()));

        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        User user = signUpReqDto.toUserEntity(Collections.singleton(authority));
        user = userRepository.save(user);

        Long id = user.getId();

        TutorDetail tutorDetail = signUpReqDto.toTutorDetailEntity(id);
        tutorDetailRepository.save(tutorDetail);

//        EntityUtils.saveEntities(signUpReqDto.getSchoolList(), id, schoolRepository, SchoolDto::toSchoolEntity);
//        EntityUtils.saveEntities(signUpReqDto.getCareerList(), id, careerRepository, CareerDto::toCareerEntity);
//        EntityUtils.saveEntities(signUpReqDto.getTagList(), id, tagRepository, TagDto::toTagEntity);

        return SignUpReqDto.from(user);
    }

    @Override
    public User getUserByUsername(String username) throws Exception {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("No user found with username: " + username));
        return user;
    }

    @Transactional
    @Override
    public void modifyProfile(SignUpReqDto signUpReqDto, String username) throws Exception {
        User user = getUserByUsername(username);

        Long id = user.getId();

        TutorDetail tutorDetail = tutorDetailRepository.findByTutorId(id);

        Set<String> userFields = new HashSet<>(Arrays.asList("name", "username", "birth", "nickname", "telephone", "password", "gender", "introduce", "hobby", "profileImg"));
        Set<String> tutorDetailFields = new HashSet<>(Arrays.asList("consultMajor1", "consultMajor2", "consultMajor3"));

        updateEntityFields(user, signUpReqDto, userFields, false);
        updateEntityFields(tutorDetail, signUpReqDto, tutorDetailFields, false);



        List<SchoolDto> schoolList = signUpReqDto.getSchoolList();
        List<TagDto> tagList = signUpReqDto.getTagList();
        List<CareerDto> careerList = signUpReqDto.getCareerList();

        // SchoolList, TagList, CareerList에 대해 업데이트
        EntityUtils.processEntities(
                signUpReqDto.getSchoolList(),
                id,
                schoolRepository,
                SchoolDto::toSchoolEntity,
                (tutorId, idx) -> schoolRepository.findByTutorIdAndIdx(tutorId, idx),
                (entity, dto, fields, isUpdate) -> updateEntityFields(entity, dto, fields, isUpdate),
                dto -> ((SchoolDto) dto).getIdx()
        );

        EntityUtils.processEntities(
                signUpReqDto.getTagList(),
                id,
                tagRepository,
                TagDto::toTagEntity,
                (tutorId, idx) -> tagRepository.findByTutorIdAndIdx(tutorId, idx),
                (entity, dto, fields, isUpdate) -> updateEntityFields(entity, dto, fields, isUpdate),
                dto -> ((TagDto) dto).getIdx()
        );


        EntityUtils.processEntities(
                signUpReqDto.getCareerList(),
                id,
                careerRepository,
                CareerDto::toCareerEntity,
                (tutorId, idx) -> careerRepository.findByTutorIdAndIdx(tutorId, idx),
                (entity, dto, fields, isUpdate) -> updateEntityFields(entity, dto, fields, isUpdate),
                dto -> ((CareerDto) dto).getIdx()
        );

        //TODO: List<MultipartFile> activeImg 저장해야함.
        
    }

    private <T, DTO> void updateEntityFields(T entity, DTO dto, Set<String> fieldsToCheck, boolean skip) {
        if (entity == null) {
            return;
        }
        Field[] dtoFields = dto.getClass().getDeclaredFields();
        for (Field field : dtoFields) {
            if (!skip && !fieldsToCheck.contains(field.getName())) {
                continue;
            }
            field.setAccessible(true);
            try {
                Object value = field.get(dto);
                if (value != null) {
                    Field entityField = entity.getClass().getDeclaredField(field.getName());
                    entityField.setAccessible(true);
                    entityField.set(entity, value);
                    entityField.setAccessible(false);
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                // 예외 처리
            }
            field.setAccessible(false);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public SignUpReqDto getUserWithAuthorities(String username) {
        return SignUpReqDto.from(userRepository.findOneWithAuthoritiesByUsername(username).orElse(null));
    }

    @Transactional(readOnly = true)
    @Override
    public SignUpReqDto getMyUserWithAuthorities() {
        return SignUpReqDto.from(
                SecurityUtil.getCurrentUsername()
                        .flatMap(userRepository::findOneWithAuthoritiesByUsername)
                        .orElseThrow(() -> new NotFoundMemberException("Member not found"))
        );
    }

    @Override
    public String uploadProfile(MultipartFile multipartFile) throws IOException {
        String url = s3Uploader.upload(multipartFile, "static/profile");
        System.out.println(url);
        return url;
    }

    @Override
    public List<String> uploadActiceImages(List<MultipartFile> MultipartFile) throws IOException {
        List<String> urlList = s3Uploader.upload(MultipartFile, "static/active");
        for (int i=0; i< urlList.size(); i++) {
            System.out.println(urlList);
        }
        return urlList;
    }

    @Override
    public boolean validUsername(String username) {

        if (userRepository.findByUsername(username)==null) return true;
        return false;
    }

    @Override
    public boolean validNickname(String nickname) {
        if (userRepository.findByNickname(nickname)==null) return true;
        return false;
    }
    @Override
    public boolean validTelephone(String telephone) {
        if (userRepository.findByTelephone(telephone)==null) return true;
        return false;
    }

    @Override
    public MentorHomeRespDto mentorHome() {
        // 유저부터
        Long userid = 10L;
        // 유저 정보부터 가져오자
        MentorHomeRespDto mentorHomeRespDto = new MentorHomeRespDto();
        mentorHomeRespDto = userRepository.findById(userid).get().toHomeDto();

        // Consulting 정보





        return null;
    }
}
