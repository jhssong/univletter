package com.jhssong.univletter.global.config.security;

import com.jhssong.univletter.global.config.security.auth.service.AuthFilter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Value("${jwt.secret}")
    String secretKey;

    @Value("${swagger.username}")
    String swaggerUsername;

    @Value("${swagger.password}")
    String swaggerPassword;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(request -> localCorsConfig()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").hasRole("SWAGGER")
                        .requestMatchers("/api/admin/login").permitAll()
                        .requestMatchers("/api/admin/**", "/api/test/**").hasRole("ADMIN")
                        .anyRequest().permitAll()
                )
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(authFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    private CorsConfiguration localCorsConfig() {
        var corsConfig = new org.springframework.web.cors.CorsConfiguration();
        corsConfig.setAllowedOriginPatterns(List.of("http://localhost:*", "https://univletter.jhssong.com"));
        corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        corsConfig.setAllowedHeaders(List.of("*"));
        corsConfig.setAllowCredentials(true);
        return corsConfig;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails user = User.withUsername(swaggerUsername)
                .password(passwordEncoder.encode(swaggerPassword))
                .roles("SWAGGER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public AuthFilter authFilter() {
        return new AuthFilter(secretKey);
    }
}
