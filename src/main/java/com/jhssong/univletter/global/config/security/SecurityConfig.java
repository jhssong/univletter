package com.jhssong.univletter.global.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/**").authenticated()
                        .requestMatchers("/api/test/**").permitAll()
                        // 다른 모든 요청은 허용
                        .anyRequest().permitAll()
                )
                // 폼 로그인 설정 (필요 없다면 제거 가능)
                // REST API에서는 보통 토큰 기반 인증을 사용하므로 formLogin은 필요 없을 수 있습니다.
                // .formLogin(formLogin -> formLogin.permitAll())
                // HTTP Basic 인증 설정 (간단한 테스트용)
                .httpBasic(httpBasic -> httpBasic.init(http)); // HTTP Basic 인증 활성화 (간단한 테스트용)

        return http.build();
    }
}
