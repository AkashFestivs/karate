package com.ama.karate.configuration;
import java.util.List;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.ama.karate.dto.AuthDto;
import com.ama.karate.service.AuthService;
import com.ama.karate.utils.Helper;

import jakarta.servlet.http.HttpSession;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final AuthService authService;
    private final HttpSession session;

    public CustomAuthenticationProvider(AuthService authService, HttpSession session) {
        this.authService = authService;
        this.session = session;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String phoneNo = authentication.getName();
        String password = (String) authentication.getCredentials();

        AuthDto reqAuthDto = new AuthDto();
        reqAuthDto.setPhoneNo(phoneNo);
        reqAuthDto.setPassword(password);
        boolean isAuthenticated = authService.isAuthenticated(reqAuthDto);

        if (isAuthenticated) {
            // Set session attributes
            String sessionKey = Helper.generateRandomString(30);
            boolean sessionRes = authService.setSessionInRedis(sessionKey, phoneNo);
            session.setAttribute("phoneNo", phoneNo);
            session.setAttribute("authenticated", true);
            session.setAttribute("sessionKey", sessionKey);

            if (sessionRes) {
                return new UsernamePasswordAuthenticationToken(
                    phoneNo, password, List.of(new SimpleGrantedAuthority("ROLE_USER"))
                );
            } else {
                throw new RuntimeException("Session creation failed");
            }
        } else {
            throw new BadCredentialsException("Invalid credentials");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}