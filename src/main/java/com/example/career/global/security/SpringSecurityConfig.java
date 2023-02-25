//package com.example.career.global.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SpringSecurityConfig  {
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//        http
//                .csrf().disable()
//                    .authorizeHttpRequests()
//                    .requestMatchers("/authentication/**", "user/main/").hasRole("ADMIN")
//                .and()
//                    .authorizeHttpRequests()
//                    .requestMatchers("/index", "/", "/main").permitAll() // 해당 요청들은 모두 승인해준다.
//                    .anyRequest() //그 외의 모든 요청은
//                    .authenticated() //인증이 필요하다..(모두 권한을 허가)
//                .and() //그리고
//                    .formLogin() //로그인 화면은
//                    .loginPage("/user/signin") //해당 GET요청으로 응답해주면되고
//                    .loginProcessingUrl("/auth/signin") //로그인 submit 요청시에 Post요청으로 /auth/signin 요청을 해라.
//                    .defaultSuccessUrl("/"); //로그인에 성공했으면 해당 url로 이동을 해라.
//        return http.build();
//    }
//}
