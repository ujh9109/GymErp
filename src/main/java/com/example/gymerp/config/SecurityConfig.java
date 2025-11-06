package com.example.gymerp.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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
            // 1. CSRF 비활성화 (API 테스트 및 SPA 환경용)
            .csrf(csrf -> csrf.disable())
            
            // 2. CORS 설정 적용
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            
            // 3. 요청 권한 제어
            .authorizeHttpRequests(auth -> auth
                
                // ✅ CORS preflight 요청 허용
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                // ✅ Swagger 관련 경로 허용
                .requestMatchers(SWAGGER).permitAll()

                // ✅ 로그인/로그아웃 경로 허용
                .requestMatchers("/v1/emp/login", "/v1/emp/logout").permitAll()
                
                // ✅ 회원 및 판매 관련 API 경로 허용 (인증 없이 접근 가능하도록 설정)
                .requestMatchers("/v1/member/**", "/v1/sales/**").permitAll()

                // ✅ PT 및 일정 관련 API (Swagger 테스트용)
                .requestMatchers("/v1/pt/**").permitAll()
                .requestMatchers("/v1/schedule/**").permitAll()

                // ✅ 그 외 모든 요청은 인증 필요
                .anyRequest().authenticated()
            )
            
            // 4. 기본 폼 로그인, HTTP Basic 비활성화
            .formLogin(login -> login.disable())
            .httpBasic(basic -> basic.disable());

        return http.build();
    }

    // ✅ PasswordEncoder Bean
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ✅ AuthenticationManager (타입 미스 없이 설정)
    @Bean
    public AuthenticationManager authenticationManager(
            HttpSecurity http,
            PasswordEncoder encoder,
            UserDetailsService userDetailsService) throws Exception {

        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(encoder)
                .and()
                .build();
    }

    // ✅ CORS 설정 (React Vite dev server 5173)
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        
        // 허용 출처 설정
        config.setAllowedOrigins(List.of(
            "http://localhost:5173", // 프론트
            "http://localhost:9000"  // 스웨거/백엔드 자체
        ));
        
        // 허용 HTTP 메서드 설정
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));

        // 모든 헤더 허용
        config.setAllowedHeaders(List.of("*"));
        
        // 인증 정보 (쿠키 등) 전송 허용
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}