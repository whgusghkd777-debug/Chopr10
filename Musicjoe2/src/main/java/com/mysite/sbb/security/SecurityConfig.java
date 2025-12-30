package com.mysite.sbb.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
           .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
    .requestMatchers(new AntPathRequestMatcher("/css/**")).permitAll()
    .requestMatchers(new AntPathRequestMatcher("/js/**")).permitAll()
    .requestMatchers(new AntPathRequestMatcher("/images/**")).permitAll()
    // .hasAuthority 대신 .hasRole 사용 (DB에 ROLE_ADMIN으로 저장되어 있어야 함)
    .requestMatchers(new AntPathRequestMatcher("/admin/**")).hasRole("ADMIN")
    .requestMatchers(new AntPathRequestMatcher("/music/delete/**")).hasRole("ADMIN") // 삭제 권한 추가
    .requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
                
            .csrf((csrf) -> csrf.disable())
            .headers((headers) -> headers
                .frameOptions((frameOptions) -> frameOptions.sameOrigin()))
            .formLogin((formLogin) -> formLogin
                .loginPage("/user/login")
                .defaultSuccessUrl("/music/list")) // 로그인 성공 시 이동할 곳
            .logout((logout) -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                .logoutSuccessUrl("/music/list")
                .invalidateHttpSession(true));
        
        return http.build();
    }
    
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}