package com.example.iManager.controller;

import com.example.iManager.requestDTO.UserRequestDTO;
import com.example.iManager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    UserService userService;

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


}
