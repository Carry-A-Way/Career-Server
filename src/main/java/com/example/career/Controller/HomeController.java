package com.example.career.Controller;

import com.example.career.domain.user.Entity.TestEntity;
import com.example.career.domain.user.Entity.User;
import com.example.career.domain.user.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor

public class HomeController {

    final UserRepository careerRepository;

    @GetMapping("/test")
    public List<User> test(){
        return careerRepository.findAll();
    }
    @PostMapping("/test/insert")
    public String testInsert(){

        return "성공";
    }
    @GetMapping("/api/hello")
    public String test2() {
        return "Hello, world!";
    }
}
