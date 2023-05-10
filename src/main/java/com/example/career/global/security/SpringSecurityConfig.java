//package com.example.career.global.security;
//
//import com.example.career.global.jwt.JwtAccessDeniedHandler;
//import com.example.career.global.jwt.JwtAuthenticationEntryPoint;
//import com.example.career.global.jwt.JwtSecurityConfig;
//import com.example.career.global.jwt.TokenProvider;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.filter.CorsFilter;
//
//@Configuration
//@EnableWebSecurity
//public class SpringSecurityConfig {
//    private final TokenProvider tokenProvider;
//    private final CorsFilter corsFilter;
//    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
//    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
//
//    public SecurityConfig(
//            TokenProvider tokenProvider,
//            CorsFilter corsFilter,
//            JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
//            JwtAccessDeniedHandler jwtAccessDeniedHandler
//    ) {
//        this.tokenProvider = tokenProvider;
//        this.corsFilter = corsFilter;
//        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
//        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity
//                // token을 사용하는 방식이기 때문에 csrf를 disable합니다.
//                .csrf().disable()
//
//                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
//
//                .exceptionHandling()
//                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
//                .accessDeniedHandler(jwtAccessDeniedHandler)
//
//                // enable h2-console
//                .and()
//                .headers()
//                .frameOptions()
//                .sameOrigin()
//
//                // 세션을 사용하지 않기 때문에 STATELESS로 설정
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//
//                .and()
//                .authorizeHttpRequests()
//                .requestMatchers("/api/hello", "/api/authenticate", "/api/signup").permitAll()
//                .requestMatchers(PathRequest.toH2Console()).permitAll()
//                .anyRequest().authenticated()
//
//                .and()
//                .apply(new JwtSecurityConfig(tokenProvider));
//
//        return httpSecurity.build();
//    }
////    @Bean
////    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//////        http
//////                .csrf().disable()
//////                    .authorizeHttpRequests()
//////                    .requestMatchers("/authentication/**", "user/main/").hasRole("ADMIN")
//////                .and()
//////                    .authorizeHttpRequests()
//////                    .requestMatchers("/index", "/", "/main").permitAll() // 해당 요청들은 모두 승인해준다.
//////                    .anyRequest() //그 외의 모든 요청은
//////                    .authenticated() //인증이 필요하다..(모두 권한을 허가)
//////                .and() //그리고
//////                    .formLogin() //로그인 화면은
//////                    .loginPage("/user/signin") //해당 GET요청으로 응답해주면되고
//////                    .loginProcessingUrl("/auth/signin") //로그인 submit 요청시에 Post요청으로 /auth/signin 요청을 해라.
//////                    .defaultSuccessUrl("/"); //로그인에 성공했으면 해당 url로 이동을 해라.
////        return http.build();
////    }
////    @Bean
////    public PasswordEncoder passwordEncoder(){
////        return new BCryptPasswordEncoder();
////    }
//}
