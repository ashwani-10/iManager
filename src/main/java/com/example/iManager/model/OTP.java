package com.example.iManager.model;

import jakarta.persistence.*;
import lombok.Data;
import org.apache.kafka.common.protocol.types.Field;

@Entity
@Data
public class OTP {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @Column(nullable = false,unique = true)
    String email;

    @Column(nullable = false)
    String otp;

}
