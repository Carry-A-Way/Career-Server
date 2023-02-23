package com.example.career.domain.user.Controller;

import com.example.career.domain.user.Dto.UserReqDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

    @PostMapping("insert")
    public String SigninUser(@RequestBody UserReqDto userReqDto) {
        System.out.println(userReqDto.toString());
        return "통신성공";
    }
}
