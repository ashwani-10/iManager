package com.example.iManager.controller;

import com.example.iManager.jwtconfig.JwtUtil;
import com.example.iManager.model.OTP;
import com.example.iManager.requestDTO.LoginRequestDTO;
import com.example.iManager.requestDTO.UserRequestDTO;
import com.example.iManager.service.UserService;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin("*")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    JwtUtil jwtUtil;

    @PostMapping("/registration")
    public ResponseEntity userRegistration(@RequestBody UserRequestDTO userRequestDTO ){
              try{
                  userService.userRegistration(userRequestDTO);
                  return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
              }
              catch (Exception e){
                  return new ResponseEntity<>("registration failed",HttpStatus.INTERNAL_SERVER_ERROR);
              }
    }

//    @PostMapping("/email/verification")
//    public ResponseEntity emailVerification(@RequestBody OTP credentials){
//        try {
//            userService.emailVerification(credentials);
//            return new ResponseEntity("Email verification Successfully",HttpStatus.OK);
//        }catch (Exception e){
//            return new ResponseEntity("Email verification failed",HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @GetMapping("/test")
    public String test(@RequestHeader("Authorization") String authHeader){
        String token = authHeader.substring(7);
        System.out.println(token);
        String username = jwtUtil.extractUsername(token);
        System.out.println(username);
        System.out.println("endpoint hi hua");
        if(jwtUtil.validateToken(token,username)){
            return username + "testing verified";
        }
        return "testing failed";
    }


}
