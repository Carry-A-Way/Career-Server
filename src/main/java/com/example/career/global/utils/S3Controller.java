//package com.example.career.global.utils;
//
//import com.example.career.domain.user.Service.UserService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//
//@RestController
//@RequiredArgsConstructor
//public class S3Controller {
//    private final UserService userService;
//
//    @PostMapping("file/profile")
//    public String uploadProfileImage(@RequestParam("image")MultipartFile multipartFile) throws IOException {
//        System.out.println("test");
//        return userService.uploadProfile(multipartFile);
//    }
//
//    @PostMapping("file/test")
//    public String test() {
//        return "test";
//    }
////    @PostMapping("file/images")
////    public String uploadActiveImages(@RequestParam("image")MultipartFile multipartFile) throws IOException {
////        return userService.uploadProfile(multipartFile);
////    }
//
//}
