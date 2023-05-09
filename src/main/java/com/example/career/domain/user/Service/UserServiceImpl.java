package com.example.career.domain.user.Service;

import com.example.career.domain.user.Dto.UserReqDto;
import com.example.career.domain.user.Dto.SignUpReqDto;
import com.example.career.domain.user.Entity.User;
import com.example.career.domain.user.Exception.PasswordWrongException;
import com.example.career.domain.user.Exception.UsernameWrongException;
import com.example.career.domain.user.Repository.UserRepository;
import com.example.career.global.security.MOpOperator;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;
    PasswordEncoder passwordEncoder;

//    @Override
//    @Transactional
//    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
//        return userRepository.findOneWithAuthoritiesByUsername(loginId)
//                .map(user -> createUser(loginId, user))
//                .orElseThrow(() -> new UsernameNotFoundException(loginId + " -> 존재 하지 않음."));
//    }
//
//    /**
//     * Security User 정보를 생성한다.
//     */
//    private org.springframework.security.core.userdetails.User createUser(String loginId, MOpOperator operatorDTO) {
//        System.out.println(operatorDTO.getIsUse());
//        System.out.println("hererere");
//        if(!"Y".equals(operatorDTO.getIsUse())){
//            throw new BadCredentialsException(loginId + " -> 활성화 되어있지 않습니다.");
//        }
//        List<GrantedAuthority> grantedAuthorities = operatorDTO.getAuthorities().stream()
//                .map(authority -> new SimpleGrantedAuthority(authority.getAthrNm()))
//                .collect(Collectors.toList());
//        return new org.springframework.security.core.userdetails.User(
//                operatorDTO.getLoginId(),
//                operatorDTO.getOprrPswd(),
//                grantedAuthorities);
//    }

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
    @Override
    public User signUp(SignUpReqDto signUpReqDto) {
        String passwd = signUpReqDto.getPassword();
        signUpReqDto.setPassword(passwordEncoder.encode(passwd));
        User user = signUpReqDto.toUserEntity();
        return userRepository.save(user);
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
}
