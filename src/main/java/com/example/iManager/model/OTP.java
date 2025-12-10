package com.example.iManager.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OTP {
    Integer id;

    String email;

    String otp;

}
