package com.example.iManager.service;

import com.example.iManager.model.OTP;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class OtpService {

    public OTP otpGenerator(String email){
        SecureRandom random = new SecureRandom();
        String otp = String.valueOf(100000 + random.nextInt(900000));

        return OTP.builder()
                .email(email)
                .otp(otp)
                .build();
    }
}
