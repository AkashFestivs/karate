package com.ama.karate.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import static com.ama.karate.utils.JsonKeyService.getJsonKey;
import com.ama.karate.utils.RedisService;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomAuthenticationProvider customAuthProvider;

    @Autowired RedisService rs;

    public SecurityConfig(CustomAuthenticationProvider customAuthProvider) {
        this.customAuthProvider = customAuthProvider;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authenticationProvider(customAuthProvider)
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/authenticate", "/forgot-password", "/change-password","/read-excel").permitAll()
                .anyRequest().access((authentication, context) -> checkSessionAndHeader(context.getRequest()))
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
            )
            .httpBasic(withDefaults());
    
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    private AuthorizationDecision checkSessionAndHeader(HttpServletRequest request) {
        try{
            String sessionKeyHeader = request.getHeader("sessionKey");
            boolean isKeyInRedis = sessionKeyHeader != null && rs.getSession(sessionKeyHeader) != null;
            String SessionData = rs.getSession(sessionKeyHeader);
            String phoneNo = getJsonKey(SessionData, "phoneNo");
            String userRole = getJsonKey(SessionData, "userRole");
            request.setAttribute("phoneNo", phoneNo);
            request.setAttribute("userRole", userRole);
            return new AuthorizationDecision(isKeyInRedis);
        }catch(Exception e){
            return new AuthorizationDecision(false);
        }
    }
}
