package com.example.iManager.service;

import com.example.iManager.enums.PaymentStatus;
import com.example.iManager.model.Organization;
import com.example.iManager.model.Payment;
import com.example.iManager.model.User;
import com.example.iManager.requestDTO.OrgRequestDTO;
import com.example.iManager.requestDTO.UserRequestDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


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

    public Payment paymentMapper(OrgRequestDTO orgRequestDTO) throws JsonProcessingException {
        Payment payment = new Payment();
        orgRequestDTO.setPassword(passwordEncoder.encode(orgRequestDTO.getPassword()));
        payment.setOrgData(new ObjectMapper().writeValueAsString(orgRequestDTO));
        payment.setStatus(PaymentStatus.PENDING);
        payment.setCreated_At(LocalDateTime.now());
        return payment;
    }
}
