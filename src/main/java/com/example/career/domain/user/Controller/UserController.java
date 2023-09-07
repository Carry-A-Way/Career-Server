package com.example.career.domain.user.Controller;

import com.example.career.domain.user.Dto.*;
import com.example.career.domain.user.Entity.*;
import com.example.career.domain.user.Service.*;
import com.example.career.global.annotation.Authenticated;
import com.example.career.global.valid.ValidCheck;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.List;

import java.io.IOException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final TutorDetailService tutorDetailService;
    private final SchoolService schoolService;
    private final TagService tagService;
    private final CareerService careerService;
    
    // 로그인
    @PostMapping("signin")
    public ValidCheck signIn(@RequestBody UserReqDto userReqDto) {
        User user = userService.signIn(userReqDto);
        ValidCheck validCheck = new ValidCheck(user);
        
        return validCheck;
    }

    // 회원가입 버튼
    // TODO : jwt token
    @Transactional
    @PostMapping("signup/mentor")
    public ResponseEntity<SignUpReqDto> signUp(@RequestBody SignUpReqDto signUpReqDto) throws IOException {
        return ResponseEntity.ok(userService.signup(signUpReqDto));
    }

    @Authenticated
    @GetMapping("/mentor/profile")
    public ResponseEntity<Object> getProfile(HttpServletRequest request) throws Exception {
        String username = (String) request.getAttribute("subject");

        try {
            User user = userService.getUserByUsername(username);
            Long id = user.getId();
            SignUpReqDto signUpReqDto = SignUpReqDto.from(user);

            TutorDetail tutorDetail = tutorDetailService.getTutorDetailByTutorId(id);
            signUpReqDto.setConsultMajor1(tutorDetail.getConsultMajor1());
            signUpReqDto.setConsultMajor2(tutorDetail.getConsultMajor2());
            signUpReqDto.setConsultMajor3(tutorDetail.getConsultMajor3());

            List<School> schoolList = schoolService.getSchoolByTutorId(id);
            List<Tag> tagList = tagService.getTagByTutorId(id);
            List<Career> careerList = careerService.getCareerByTutorId(id);

            List<SchoolDto> schoolDtoList = schoolList.stream().map(SchoolDto::from)
                    .collect(Collectors.toList());
            List<TagDto> tagDtoList = tagList.stream().map(TagDto::from)
                    .collect(Collectors.toList());
            List<CareerDto> careerDtoList = careerList.stream().map(CareerDto::from)
                    .collect(Collectors.toList());


            signUpReqDto.setSchoolList(schoolDtoList);
            signUpReqDto.setTagList(tagDtoList);
            signUpReqDto.setCareerList(careerDtoList);

            return ResponseEntity.ok(signUpReqDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error message");
        }
    }

    @Transactional
    @Authenticated
    @PostMapping("/mentor/modify_profile")
    public ResponseEntity<Object> modifyProfile(MultipartHttpServletRequest request) throws Exception {

        String username = (String) request.getAttribute("subject");

        // 파일 데이터 추출
        MultipartFile multipartFile = request.getFile("image");

        // JSON 데이터 추출
        String jsonStr = request.getParameter("json");
        SignUpReqDto user = new ObjectMapper().readValue(jsonStr, SignUpReqDto.class);

        if (multipartFile != null) {
            String url = userService.uploadProfile(multipartFile);
            user.setProfileImg(url);
        }

        userService.modifyProfile(user, username);
        
        return ResponseEntity.ok(null);
    }

    @PostMapping("/signup")
    public ResponseEntity<SignUpReqDto> signup(@Valid @RequestBody SignUpReqDto userDto) {
        return ResponseEntity.ok(userService.signup(userDto));
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<SignUpReqDto> getMyUserInfo(HttpServletRequest request) {
        return ResponseEntity.ok(userService.getMyUserWithAuthorities());
    }

    @GetMapping("/user/{username}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<SignUpReqDto> getUserInfo(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserWithAuthorities(username));
    }
    //{"username": "ss", "temp": "sssss"}
    // username 중복확인
    @PostMapping("valid/username")
    public ValidCheck validUsername(@RequestBody SignUpReqDto signUpReqDto) {
        boolean isValid = userService.validUsername(signUpReqDto.getUsername());
        ValidCheck validCheck = new ValidCheck(isValid);
        
        return validCheck;
    }

    // nickname 중복확인
    @PostMapping("valid/nickname")
    public ValidCheck validNickname(@RequestBody SignUpReqDto signUpReqDto) {
        boolean isValid = userService.validNickname(signUpReqDto.getNickname());
        ValidCheck validCheck = new ValidCheck(isValid);

        return validCheck;
    }

    // telephone 중복확인
    @PostMapping("valid/telephone")
    public ValidCheck validTelephone(@RequestBody SignUpReqDto signUpReqDto) {
        boolean isValid = userService.validTelephone(signUpReqDto.getTelephone());
        ValidCheck validCheck = new ValidCheck(isValid);
        
        return validCheck;
    }

    //landing 홈페이지 /tutor/home

    @PostMapping("/tutor/home")
    public ValidCheck landingHomementor() { // jwt 토큰 인증

        return null;
    }
    // 프로필 이미지
    @PostMapping("file/profile")
    public String uploadProfileImage(@RequestParam("image") MultipartFile multipartFile) throws IOException {
        return userService.uploadProfile(multipartFile);
    }
    // 엑티브 이미지 여러장
    @PostMapping("file/active")
    public List<String> uploadActiveImages(@RequestParam("images") List<MultipartFile> multipartFiles) throws IOException {
        return userService.uploadActiceImages(multipartFiles);
    }
    @GetMapping("file/test")
    public String test()  {
        return "test";
    }


}