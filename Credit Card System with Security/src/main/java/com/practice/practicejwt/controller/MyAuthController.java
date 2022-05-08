package com.practice.practicejwt.controller;

import com.practice.practicejwt.payload.LoginDto;
import com.practice.practicejwt.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class MyAuthController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider jwtProvider;

    @PostMapping
    public String getAuth(@RequestBody LoginDto loginDto){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));

            String token = jwtProvider.generateToken(loginDto.getUsername());
           return token;
        }catch (Exception e){
           return "Password not found";
        }
    }
}
