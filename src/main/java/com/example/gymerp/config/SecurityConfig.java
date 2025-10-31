package com.example.gymerp.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
            "/configuration/ui", "/configuration/security", "/upload/**",
            "/v1/product/**"
            
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // csrf 일단 비활성화 (API 테스트 용도)
            .cors(cors -> cors.configurationSource(corsConfigurationSource())) // cors 설정 (react)
            .authorizeHttpRequests(auth -> auth // 요청 권한 제어
                .requestMatchers(SWAGGER).permitAll() // Swagger 허용
                .requestMatchers("/v1/login/**").permitAll() // 로그인 허용
                .anyRequest().permitAll()       // 전체 허용 (초기 개발용)
            )
            
            // 로그인 폼 비활성화
            .formLogin(login -> login.disable())
            // 세션 기반 로그인도 비활성화 (JWT 같은 stateless용)
            .httpBasic(basic -> basic.disable());
        
        return http.build();
    }
    
    // BCryptPasswordEncoder 등록
    @Bean
    PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
    }
    
    //인증 메니저 객체를 bean 으로 만든다. (Spring Security 가 자동 로그인 처리할때도 사용되는 객체)
  	@Bean
  	AuthenticationManager authenticationManager(HttpSecurity http,
  			BCryptPasswordEncoder encoder, UserDetailsService service) throws Exception{
  		//적절한 설정을한 인증 메니저 객체를 리턴해주면 bean 이 되어서 Spring Security 가 사용한다 
  		return http.getSharedObject(AuthenticationManagerBuilder.class)
  				.userDetailsService(service)
  				.passwordEncoder(encoder)
  				.and()
  				.build();
  	}
  	
    // REACT(React:5173)에서의 요청을 허용 
    // 나중에 REACT Repository 만들어지면 설정해주세요
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:5173")); // Vite 개발 서버
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // 모든 경로에 적용
        return source;
    }
}
