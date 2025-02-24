package com.example.iManager.kafkaProducerDTO;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserRegistrationMessageDTO {
    String name;
    String email;
    String otp;
}
