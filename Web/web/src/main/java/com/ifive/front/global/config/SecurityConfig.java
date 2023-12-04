package com.ifive.front.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;



@Configuration
@EnableWebSecurity // 모든 URL이 시큐리티 제어를 받음.
public class SecurityConfig {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                .requestMatchers(new AntPathRequestMatcher("/**")).permitAll()) // 모든 인증되지않은 요청허락, 로그인없이도 접근가능
            .headers((headers) -> headers.addHeaderWriter(new XFrameOptionsHeaderWriter(
                XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))) // 클릭재킹(CSRF) 공격방지, 다른사이트에서 프레임으로 포함 불가
            .formLogin((formLogin) -> formLogin
                .loginPage("/member/signin")    // 로그인 페이지 URL설정
                .defaultSuccessUrl("/") // 로그인 성공시 이동할 URL설정
                .usernameParameter("memberName"))
            .logout((logout) -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/member/signout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true))
            ;
        return http.build();
    }

    @Bean // 패스워드 암호화 빈
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    } 

    // AuthenticationManager는 스프링 시큐리티 인증 담당함.
    // AuthenticationManager의 사용자 인증은 IfiveMemberSecurityService와 PasswordEncoder를 사용함
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}