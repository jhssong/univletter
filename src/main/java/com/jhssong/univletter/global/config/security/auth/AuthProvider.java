package com.jhssong.univletter.global.config.security.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * For Admin Page request
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AuthProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String rawKey = authentication.getCredentials().toString();

        log.info("어드민 페이지 인증 시도 - 사용자: {}, 입력된 키 길이: {}", username, rawKey.length());

        if (passwordEncoder.matches(rawKey, userDetailsService.loadUserByUsername("admin").getPassword())) {
            UserDetails adminUserDetails = userDetailsService.loadUserByUsername("admin");

            if (adminUserDetails != null) {
                log.info("인증에 성공했습니다.");
                return new UsernamePasswordAuthenticationToken(
                        adminUserDetails, rawKey, adminUserDetails.getAuthorities());
            } else {
                log.error("ADMIN UserDetails를 로드할 수 없습니다.");
                throw new BadCredentialsException("Invalid admin configuration");
            }
        } else {
            log.info("인증에 실패했습니다 - 입력된 키가 일치하지 않습니다.");
            throw new BadCredentialsException("Invalid Key");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
