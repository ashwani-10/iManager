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

    @PostMapping("/login")
    public ResponseEntity userLogin(@RequestBody LoginRequestDTO request){

        if(userService.userLogin(request)){
            String token = jwtUtil.generateToken(request.getUsername());
            System.out.println(token);
            return new ResponseEntity(token,HttpStatus.OK);
        }
        return new ResponseEntity("Invalid Creds",HttpStatus.BAD_REQUEST);
    }


    @PostMapping("/email/verification")
    public ResponseEntity emailVerification(@RequestBody OTP creds){
        try {
            userService.emailVerification(creds);
            return new ResponseEntity("Email verification Successfully",HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity("Email verification failed",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/invite")
    public ResponseEntity inviteMember(@RequestHeader("Authorization") String authHeader,
                                       @RequestParam String inviteEmail){
        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);

        if(jwtUtil.validateToken(token,username)) {
            try {
                userService.inviteMember(username,inviteEmail);
                return new ResponseEntity("Invited Successfully",HttpStatus.OK);
            }catch (Exception e){
                return new ResponseEntity("Inviting user failed",HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity("Not a valid user",HttpStatus.UNAUTHORIZED);
    }

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
