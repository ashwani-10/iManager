package com.example.iManager.repository;

import com.example.iManager.model.OTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpRepository extends JpaRepository<OTP,Integer> {
    OTP findByEmail(String email);
}
