package com.example.iManager.service;

import com.example.iManager.model.Organization;
import com.example.iManager.model.User;
import com.example.iManager.requestDTO.OrgRequestDTO;
import com.example.iManager.requestDTO.UserRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class Mapper {

    @Autowired
    PasswordEncoder passwordEncoder;

    public  User userMapper(UserRequestDTO userRequestDTO){

        return User.builder()
                .name(userRequestDTO.getName())
                .email(userRequestDTO.getEmail())
                .password(passwordEncoder.encode(userRequestDTO.getPassword()))
                .build();
    }

    public Organization orgMapper(OrgRequestDTO orgRequestDTO){
        return Organization.builder()
                .name(orgRequestDTO.getName())
                .email(orgRequestDTO.getEmail())
                .password(passwordEncoder.encode(orgRequestDTO.getPassword()))
                .build();
    }
}
