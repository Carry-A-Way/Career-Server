//package com.example.career.global.security;
//
//import com.example.career.domain.user.Entity.User;
//import com.example.career.domain.user.Repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//import java.util.Random;
//
//@RequiredArgsConstructor
//@Service
//public class UserDetail implements UserDetailsService {
//
//    private final UserRepository userRepository;
//
//    public Long save(User infoDto){
//
//        System.out.println(String.valueOf(infoDto));
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        infoDto.setPassword(encoder.encode(infoDto.getPassword()));
//
//        boolean check = userRepository.existsByUsername(infoDto.getUsername());
//        if(check) return null; // 이미 존재할 경우
//        else{
//            return userRepository.save(User.builder()
//                    .username(infoDto.getUsername())
//                    .password(infoDto.getPassword())
//                    .nickname(infoDto.getNickname())
//                    .role("USER").build()).getId();
//        }
//
//
//
//    }
//
//    @Override
//    public User loadUserByUsername(String username) throws UsernameNotFoundException {
//        // 시큐리티에서 지정한 서비스이기 때문에 이 메소드를 필수로 구현
//        User user =  userRepository.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("등록되지 않은 사용자 입니다."));
//        return user;
//    }
//
//}