package com.ama.karate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ama.karate.dao.loginDao;
import com.ama.karate.dto.AuthDto;
import com.ama.karate.utils.PasswordService;

@Service
public class AuthService {

    @Autowired loginDao login;

    @Autowired PasswordService passwordService;

    //to check if user entered password matched the database password
    public boolean isAuthenticated(AuthDto reqAuthDto){
        AuthDto dbAuth = login.userPassword(reqAuthDto.getPhoneNo());
        if(dbAuth != null){
            boolean authSuccess = passwordService.comparePassword(reqAuthDto.getPassword(), dbAuth.getPassword());
            return authSuccess;
        }else{
            return false;
        }

    }
}
