package com.example.iManager.service;

import com.example.iManager.exceptions.EmailSendingFailedException;
import com.example.iManager.exceptions.RegistrationFailedException;
import com.example.iManager.kafkaProducerDTO.UserRegistrationMessageDTO;
import com.example.iManager.model.OTP;
import com.example.iManager.model.Organization;
import com.example.iManager.requestDTO.LoginRequestDTO;
import com.example.iManager.requestDTO.OrgRequestDTO;
import com.example.iManager.util.DbApi;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;

@Service
public class OrgService {
    private final OtpService otpService;
    private final Mapper mapper;
    private final KafkaProducerService kafkaProducer;
    private final DbApi dbApi;
    private final PasswordEncoder passwordEncoder;
    private final PaymentService paymentService;

    public OrgService(OtpService otpService, Mapper mapper, KafkaProducerService kafkaProducer, DbApi dbApi, PasswordEncoder passwordEncoder, PaymentService paymentService) {
        this.otpService = otpService;
        this.mapper = mapper;
        this.kafkaProducer = kafkaProducer;
        this.dbApi = dbApi;
        this.passwordEncoder = passwordEncoder;
        this.paymentService = paymentService;
    }

    public void orgRegistration(OrgRequestDTO request){

        try {
            System.out.println("hitting sb api method");
            Organization org = mapper.orgMapper(request);
            dbApi.createOrg(org); // mapped the request with the org entity
            OTP otp = otpService.otpGenerator(request.getEmail()); // generated otp for the email verification

//            orgRepository.save(org);
//            otpRepository.save(otp);

            sendVerificationMail(request,otp.getOtp());
        } catch (Exception e) {
            throw new RegistrationFailedException("Failed to complete registration",e);
        }
    }

    private void sendVerificationMail(OrgRequestDTO org,String otp) {
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

    public boolean userLogin(LoginRequestDTO request) throws IOException {
        String endPoint = "http://localhost:8082/db/api/org/get";
        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("orgEmail", request.getUsername());
        Object obj = dbApi.makeGetCall(endPoint,"",queryParams);
        ObjectMapper objectMapper = new ObjectMapper();

        Organization org = objectMapper.convertValue(obj, Organization.class);

        String actualPassword = request.getPassword();
        if(passwordEncoder.matches(actualPassword,org.getPassword())){
            return true;
        }
        return false;
    }
}
