package com.example.gymerp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {
	
    private static final String[] SWAGGER = {
            "/swagger-ui.html", "/swagger-ui/**",
            "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**",
            "/configuration/ui", "/configuration/security"
            
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())       // 초기엔 꺼두면 테스트 편함
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(SWAGGER).permitAll()
                .anyRequest().permitAll()       // 전체 허용 (초기 개발용)
            );
        return http.build();
    }
}
