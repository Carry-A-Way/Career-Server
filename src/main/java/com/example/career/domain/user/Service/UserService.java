package com.example.career.domain.user.Service;

import com.example.career.domain.community.Dto.Brief.UserBriefWithRate;
import com.example.career.domain.user.Dto.*;
import com.example.career.domain.user.Entity.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {
    User signIn(UserReqDto userReqDto);
//    public User signUp(SignUpReqDto signUpReqDto);
    @Transactional
    SignUpReqDto signupTutor(SignUpReqDto userDto);

    @Transactional
    SignUpReqDto signupStudent(SignUpReqDto userDto);
    User getUserByUsername(String username) throws Exception;
    @Transactional
    void modifyProfileTutor(SignUpReqDto signUpReqDto, String username) throws Exception;
    @Transactional
    void modifyProfileStudent(SignUpReqDto signUpReqDto, String username) throws Exception;
    @Transactional
    void modifyMentorTagList(List<TagDto> tagDtoList, Long id);
    @Transactional
    void modifyMentorSchoolList(List<SchoolDto> schoolDtoList, Long id);
    @Transactional
    void modifyMentorCareerList(List<CareerDto> careerDtoList, Long id);
    @Transactional
    void modifyMenteeTagList(List<TagDto> tagDtoList, Long id);
    boolean validUsername(String username);
    boolean validNickname(String nickname);
    boolean validTelephone(String telephone);
    MentorHomeRespDto mentorHome();
    @Transactional(readOnly = true)
    SignUpReqDto getUserWithAuthorities(String username);
    @Transactional(readOnly = true)
    SignUpReqDto getMyUserWithAuthorities();
    String uploadProfile(MultipartFile multipartFile) throws IOException;
    List<String> uploadActiceImages(List<MultipartFile> MultipartFile) throws IOException;

    UserBriefWithRate getUserCardData(Long userId);

    void deleteListInMentorProfile(ListDeleteReqDto listDeleteReqDto, Long userId);

    void deleteListInMenteeProfile(ListDeleteReqDto listDeleteReqDto, Long userId);
}
