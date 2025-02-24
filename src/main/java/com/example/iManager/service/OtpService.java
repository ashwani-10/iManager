package com.example.iManager.service;

import com.example.iManager.model.OTP;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class OtpService {

    public OTP otpGenerator(String email){
        SecureRandom random = new SecureRandom();
        String OTP = String.valueOf(100000 + random.nextInt(900000));
        OTP otp = new OTP();
        otp.setEmail(email);
        otp.setOtp(OTP);

        return otp;
    }
}
