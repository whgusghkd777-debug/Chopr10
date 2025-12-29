package com.mysite.sbb;

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
            .authorizeHttpRequests((auth) -> auth
                .requestMatchers(new AntPathRequestMatcher("/css/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/js/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/images/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
            .csrf((csrf) -> csrf.disable())
            .formLogin((form) -> form
                .loginPage("/user/login")
                .defaultSuccessUrl("/music/list")
                .permitAll())
            .logout((logout) -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout")) // HTML과 일치시킴
                .logoutSuccessUrl("/music/list")
                .invalidateHttpSession(true))
        ;
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}