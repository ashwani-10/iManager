package com.example.iManager.service;

import com.example.iManager.exceptions.EmailSendingFailedException;
import com.example.iManager.exceptions.RegistrationFailedException;
import com.example.iManager.exceptions.UserAlreadyExistsException;
import com.example.iManager.kafkaProducerDTO.UserRegistrationMessageDTO;
import com.example.iManager.model.OTP;
import com.example.iManager.model.Organization;
import com.example.iManager.repository.OrgRepository;
import com.example.iManager.repository.OtpRepository;
import com.example.iManager.requestDTO.OrgRequestDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InterruptedIOException;
import java.security.SecureRandom;
import java.util.Optional;
import java.util.Random;

@Service
public class OrgService {
    private final OrgRepository orgRepository;
    private final OtpRepository otpRepository;
    private final OtpService otpService;
    private final Mapper mapper;
    private final KafkaProducerService kafkaProducer;

    public OrgService(OrgRepository orgRepository, OtpRepository otpRepository,
                      OtpService otpService, Mapper mapper,
                      KafkaProducerService kafkaProducer) {
        this.orgRepository = orgRepository;
        this.otpRepository = otpRepository;
        this.otpService = otpService;
        this.mapper = mapper;
        this.kafkaProducer = kafkaProducer;
    }

    @Transactional
    public void orgRegistration(OrgRequestDTO request){
        Optional<Organization> optionalOrg = orgRepository.findByEmail(request.getEmail());

        if(optionalOrg.isPresent()){
            System.out.println("User already exists");
            throw new UserAlreadyExistsException("User already exists"+request.getEmail());
        }

        try {
            Organization org = mapper.orgMapper(request); // mapped the request with the org entity
            OTP otp = otpService.otpGenerator(request.getEmail()); // generated otp for the email verification

            orgRepository.save(org);
            otpRepository.save(otp);

            sendVerificationMail(org,otp.getOtp());
        } catch (Exception e) {
            throw new RegistrationFailedException("Failed to complete registration",e);
        }
    }

    private void sendVerificationMail(Organization org,String otp) {
        final String topic = "user-registration";
        UserRegistrationMessageDTO mailDTO = UserRegistrationMessageDTO.builder()
                .name(org.getName())
                .email(org.getEmail())
                .otp(otp)
                .build();

        int retries = 3;

        while (retries-- > 0) {
            try {
                kafkaProducer.produceMessage(mailDTO, mailDTO.getEmail(), topic);
                return;
            }catch (Exception e) {
                if (retries == 0) {
                    throw new EmailSendingFailedException("Failed to send registration email after 3 retries", e);
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    throw new EmailSendingFailedException("Email sending Interrupted", ex);
                }
            }
        }
    }

}
