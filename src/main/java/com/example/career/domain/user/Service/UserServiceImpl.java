package com.example.career.domain.user.Service;

import com.example.career.domain.community.Dto.Brief.UserBriefWithRate;
import com.example.career.domain.consult.Entity.Review;
import com.example.career.domain.consult.Repository.ConsultRepository;
import com.example.career.domain.consult.Repository.ReviewRepository;
import com.example.career.domain.search.Service.SearchService;
import com.example.career.domain.user.Dto.*;
import com.example.career.domain.user.Entity.*;
import com.example.career.domain.user.Exception.DuplicateMemberException;
import com.example.career.domain.user.Exception.NotFoundMemberException;
import com.example.career.domain.user.Exception.PasswordWrongException;
import com.example.career.domain.user.Exception.UsernameWrongException;
import com.example.career.domain.user.Repository.*;
import com.example.career.domain.user.Util.SecurityUtil;
import com.example.career.global.time.KoreaTime;
import com.example.career.global.utils.S3Uploader;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final TutorDetailRepository tutorDetailRepository;
    private final StudentDetailRepository studentDetailRepository;
    private final SchoolRepository schoolRepository;
    private final TagRepository tagRepository;
    private final CareerRepository careerRepository;
    private final PasswordEncoder passwordEncoder;
    private final ConsultRepository consultRepository;
    private final S3Uploader s3Uploader;
    private final ReviewRepository reviewRepository;
    private final FAQRepository faqRepository;
    private final MentorHeartRepository mentorHeartRepository;
    private final SearchService searchService;

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
    public SignUpReqDto signupTutor(SignUpReqDto signUpReqDto) {
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

    @Transactional
    @Override
    public SignUpReqDto signupStudent(SignUpReqDto signUpReqDto) {
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

        StudentDetail studentDetail = signUpReqDto.toStudentDetailEntity(id);
        studentDetailRepository.save(studentDetail);

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
    public void modifyProfileTutor(SignUpReqDto signUpReqDto, String username) throws Exception {
        if (signUpReqDto.getPassword() != null) {
            signUpReqDto.setPassword(passwordEncoder.encode(signUpReqDto.getPassword()));
        }

        User user = getUserByUsername(username);
        user.setUpdatedAt(KoreaTime.now());

        Long id = user.getId();

        TutorDetail tutorDetail = tutorDetailRepository.findByTutorId(id);

        Set<String> userFields = new HashSet<>(Arrays.asList("email", "name", "username", "birth", "nickname", "telephone", "password", "gender", "introduce", "hobby", "profileImg"));
        Set<String> tutorDetailFields = new HashSet<>(Arrays.asList("consultMajor1", "consultMajor2", "consultMajor3", "myLife"));

        updateEntityFields(user, signUpReqDto, userFields, false);
        updateEntityFields(tutorDetail, signUpReqDto, tutorDetailFields, false);

        List<SchoolDto> schoolList = signUpReqDto.getSchoolList();
        List<TagDto> tagList = signUpReqDto.getTagList();
        List<CareerDto> careerList = signUpReqDto.getCareerList();

        modifyMentorTagList(tagList, id);
        modifyMentorCareerList(careerList, id);
        modifyMentorSchoolList(schoolList, id);

        // FAQ 저장
        List<FAQ> updatedFaqs = signUpReqDto.getFAQ().stream().map(faq -> {
            faq.setTutorId(id); // tutorId 설정
            return faq;
        }).collect(Collectors.toList());
        faqRepository.saveAll(updatedFaqs);

//        // SchoolList, TagList, CareerList에 대해 업데이트
//        EntityUtils.processEntities(
//                signUpReqDto.getSchoolList(),
//                id,
//                schoolRepository,
//                SchoolDto::toSchoolEntity,
//                (userId, idx) -> schoolRepository.findByTutorIdAndIdx(userId, idx),
//                (entity, dto, fields, isUpdate) -> updateEntityFields(entity, dto, fields, isUpdate),
//                dto -> ((SchoolDto) dto).getIdx()
//        );
//
//        EntityUtils.processEntities(
//                signUpReqDto.getTagList(),
//                id,
//                tagRepository,
//                TagDto::toTagEntity,
//                (userId, idx) -> tagRepository.findByUserIdAndIdx(userId, idx),
//                (entity, dto, fields, isUpdate) -> updateEntityFields(entity, dto, fields, isUpdate),
//                dto -> ((TagDto) dto).getIdx()
//        );
//
//
//        EntityUtils.processEntities(
//                signUpReqDto.getCareerList(),
//                id,
//                careerRepository,
//                CareerDto::toCareerEntity,
//                (userId, idx) -> careerRepository.findByTutorIdAndIdx(userId, idx),
//                (entity, dto, fields, isUpdate) -> updateEntityFields(entity, dto, fields, isUpdate),
//                dto -> ((CareerDto) dto).getIdx()
//        );

        
    }

    @Transactional
    @Override
    public void modifyProfileStudent(SignUpReqDto signUpReqDto, String username) throws Exception {
        if (signUpReqDto.getPassword() != null) {
            signUpReqDto.setPassword(passwordEncoder.encode(signUpReqDto.getPassword()));
        }

        User user = getUserByUsername(username);
        user.setUpdatedAt(KoreaTime.now());

        Long id = user.getId();

        StudentDetail studentDetail = studentDetailRepository.findByStudentId(id);


        Set<String> userFields = new HashSet<>(Arrays.asList("email", "name", "username", "birth", "nickname", "telephone", "password", "gender", "introduce", "hobby", "profileImg"));
        Set<String> studentDetailFields = new HashSet<>(Arrays.asList("interestingMajor1", "interestingMajor2", "interestingMajor3"));

        updateEntityFields(user, signUpReqDto, userFields, false);
        updateEntityFields(studentDetail, signUpReqDto, studentDetailFields, false);

        modifyMenteeTagList(signUpReqDto.getTagList(), id);

        // tagList delta version
//        EntityUtils.processEntities(
//                signUpReqDto.getTagList(),
//                id,
//                tagRepository,
//                TagDto::toTagEntity,
//                (userId, idx) -> tagRepository.findByUserIdAndIdx(userId, idx),
//                (entity, dto, fields, isUpdate) -> updateEntityFields(entity, dto, fields, isUpdate),
//                dto -> ((TagDto) dto).getIdx()
//        );
    }

    @Transactional
    @Override
    public void modifyMentorTagList(List<TagDto> tagDtoList, Long id) {
        tagRepository.deleteAllByUserId(id);
        if (tagDtoList == null) return;
        for (TagDto tagDto: tagDtoList) {
            tagRepository.save(tagDto.toTagEntity(id));
        }
    }

    @Transactional
    @Override
    public void modifyMentorSchoolList(List<SchoolDto> schoolDtoList, Long id) {
        schoolRepository.deleteAllByTutorId(id);
        if (schoolDtoList == null) return;
        for (SchoolDto schoolDto: schoolDtoList) {
            schoolRepository.save(schoolDto.toSchoolEntity(id));
        }
    }

    @Transactional
    @Override
    public void modifyMentorCareerList(List<CareerDto> careerDtoList, Long id) {
        careerRepository.deleteAllByTutorId(id);
        if (careerDtoList == null) return;
        for (CareerDto careerDto: careerDtoList) {
            careerRepository.save(careerDto.toCareerEntity(id));
        }
    }

    @Transactional
    @Override
    public void modifyMenteeTagList(List<TagDto> tagDtoList, Long id) {
        tagRepository.deleteAllByUserId(id);
        if (tagDtoList == null) return;
        for (TagDto tagDto: tagDtoList) {
            tagRepository.save(tagDto.toTagEntity(id));
        }
    }

    @Transactional
    @Override
    public void deleteListInMentorProfile(ListDeleteReqDto listDeleteReqDto, Long userId) {
        List<SchoolDto> schoolList = listDeleteReqDto.getSchoolList();
        List<TagDto> tagList = listDeleteReqDto.getTagList();
        List<CareerDto> careerList = listDeleteReqDto.getCareerList();

        for (SchoolDto schoolDto: schoolList) {
            schoolRepository.deleteByTutorIdAndIdx(userId, schoolDto.getIdx());
        }
        for (TagDto tagDto: tagList) {
            tagRepository.deleteByUserIdAndIdx(userId, tagDto.getIdx());
        }
        for (CareerDto careerDto: careerList) {
            careerRepository.deleteByTutorIdAndIdx(userId, careerDto.getIdx());
        }
    }

    @Transactional
    @Override
    public void deleteListInMenteeProfile(ListDeleteReqDto listDeleteReqDto, Long userId) {
        List<TagDto> tagList = listDeleteReqDto.getTagList();

        for (TagDto tagDto: tagList) {
            tagRepository.deleteByUserIdAndIdx(userId, tagDto.getIdx());
        }
    }

    @Override
    public ResponseEntity<String> insertHeart(User mentee, Long mentorId) {
        MentorHeart mentorHeart = MentorHeart.builder()
                .menteeId(mentee.getId())
                .mentorId(mentorId)
                .build();
        try{
            mentorHeart = mentorHeartRepository.save(mentorHeart);
            if(mentorHeart == null) {
                return  ResponseEntity.badRequest().body("저장에 실패하였습니다.");
            }
        }catch(DataIntegrityViolationException e) {
            return  ResponseEntity.badRequest().body("중복 데이터가 감지되었습니다.");

        }
        return ResponseEntity.ok("좋아요 등록에 성공하였습니다.");
    }

    @Override
    @Transactional
    public ResponseEntity<String> deleteHeart(User mentee, Long mentorId) {
        mentorHeartRepository.deleteMentorHeartByMenteeIdAndAndMentorId(mentee.getId(), mentorId);
        return ResponseEntity.ok("좋아요 취소에 성공하였습니다.");
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
    public UserBriefWithRate getUserCardData(Long menteeId, Long mentorId) {
        UserBriefWithRate userBriefWithRate = tutorDetailRepository.findUserCardData(mentorId);
        if(userBriefWithRate == null) return null;
        try{
            userBriefWithRate.setSchoolList(schoolRepository.findAllByTutorIdOrderByIdxAsc(mentorId));

        }catch (NullPointerException e) {
            userBriefWithRate.setSchoolList(null);
        }

        userBriefWithRate.setHeart(searchService.setUserHeart(menteeId, mentorId));

        try{
            userBriefWithRate.setReview(
                    reviewRepository.findAllByTutorDetailOrderByCreatedAt(
                            tutorDetailRepository.findByTutorId(
                                    userBriefWithRate.getId()
                            )
                    ).stream().map(Review::toRespDto).toList()
            );
        }catch(NullPointerException e) {
            userBriefWithRate.setReview(null);
        }
        try{
            userBriefWithRate.setFAQ(faqRepository.findAllByTutorIdOrderById(mentorId));

        }catch (NullPointerException e) {
            userBriefWithRate.setFAQ(null);
        }
        try{
            userBriefWithRate.setCareer(careerRepository.findAllByTutorId(mentorId));

        }catch (NullPointerException e) {
            userBriefWithRate.setCareer(null);
        }
        return userBriefWithRate;
    }

    @Override
    public boolean validUsername(String username) {

        if (userRepository.findByUsername(username).orElse(null) == null) return true;
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
