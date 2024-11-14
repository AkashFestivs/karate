package com.ama.karate.controller.authentication;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ama.karate.dto.AuthDto;
import com.ama.karate.utils.RedisService;

import jakarta.servlet.http.HttpSession;

@RestController
public class Login {
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private RedisService rs;

    @PostMapping("/authenticate")
    public ResponseEntity<Map<String, Object>> login(@RequestBody AuthDto loginRequest, HttpSession session) {
        Map<String, Object> response = new HashMap<>();

        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getPhoneNo(), loginRequest.getPassword())
            );

            System.out.println("Authentication successful for " + authentication.toString());
            if (authentication.isAuthenticated()) {
                String SessionKey = (String) session.getAttribute("sessionKey");
                String sessionData = rs.getSession(SessionKey);
                response.put("sessionData", sessionData);
                response.put("status", "success");
                response.put("message", "Login successful");
                String sessionKey = (String) session.getAttribute("sessionKey");
                return ResponseEntity.accepted().header("sessionKey", sessionKey).body(response);
            } else {
                response.put("status", "error");
                response.put("message", "Authorization failed");
                return ResponseEntity.status(401).body(response);
            }
        } catch (AuthenticationException e) {
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", "Internal Server Error");
            return ResponseEntity.status(401).body(response);
        }
    }

    @PostMapping("/profile")
    public ResponseEntity<Map<String, Object>> test(HttpSession session){
        Map<String, Object> response = new HashMap<>();
        String SessionKey = (String) session.getAttribute("sessionKey");
        String sessionData = rs.getSession(SessionKey);
        response.put("sessionData", sessionData);
        return ResponseEntity.accepted().body(response);
    }

}
