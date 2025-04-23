package com.example.iManager.controller;

import com.example.iManager.jwtconfig.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/token")
public class TokenVerificationController {
    JwtUtil jwtUtil;

    public TokenVerificationController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/verify")
    public ResponseEntity tokenVerify(@RequestHeader("Authorization") String authHeader){
        String token = authHeader.substring(7);
        System.out.println(token);
        String username = jwtUtil.extractUsername(token);
        System.out.println(username);
        System.out.println("endpoint hit hua");
        if(jwtUtil.validateToken(token,username)){
            System.out.println("Token Verified");
            return new ResponseEntity(username, HttpStatus.OK);
        }else{
            System.out.println("Token not verified");
            return new ResponseEntity("Invalid Token", HttpStatus.UNAUTHORIZED);
        }
    }
}
