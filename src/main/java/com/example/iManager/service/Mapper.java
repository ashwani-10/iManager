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
        User user=new User();

        user.setEmail(userRequestDTO.getEmail());
        user.setName(userRequestDTO.getName());
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));

        return user;
    }

    public Organization orgMapper(OrgRequestDTO orgRequestDTO){
        Organization org = new Organization();

        org.setName(orgRequestDTO.getName());
        org.setEmail(orgRequestDTO.getEmail());
        org.setPassword(passwordEncoder.encode(orgRequestDTO.getPassword()));
        org.setSubscription(orgRequestDTO.getSubscription());

        return org;
    }
}
