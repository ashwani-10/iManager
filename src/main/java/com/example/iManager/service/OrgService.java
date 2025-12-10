package com.example.iManager.service;

import com.example.iManager.exceptions.EmailSendingFailedException;
import com.example.iManager.exceptions.RegistrationFailedException;
import com.example.iManager.kafkaProducerDTO.UserRegistrationMessageDTO;
import com.example.iManager.model.OTP;
import com.example.iManager.model.Organization;
import com.example.iManager.requestDTO.LoginRequestDTO;
import com.example.iManager.requestDTO.OrgRequestDTO;
import com.example.iManager.requestDTO.UserRequestDTO;
import com.example.iManager.util.DbApi;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class OrgService {
    private final OtpService otpService;
    private final Mapper mapper;
    private final KafkaProducerService kafkaProducer;
    private final DbApi dbApi;
    private final PasswordEncoder passwordEncoder;
    @Value("${service.db.url}")
    private String dbUrl;

    public OrgService(OtpService otpService, Mapper mapper, KafkaProducerService kafkaProducer,
                      DbApi dbApi, PasswordEncoder passwordEncoder) {
        this.otpService = otpService;
        this.mapper = mapper;
        this.kafkaProducer = kafkaProducer;
        this.dbApi = dbApi;
        this.passwordEncoder = passwordEncoder;
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

    public OrgRequestDTO orgLogin(LoginRequestDTO request) throws IOException {
        String endPoint = dbUrl+"/db/api/org/get";
        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("orgEmail", request.getUsername());
        Object obj = dbApi.makeGetCall(endPoint,"",queryParams).getBody();
        if(obj == null){
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();

        OrgRequestDTO org = objectMapper.convertValue(obj, OrgRequestDTO.class);

        String actualPassword = request.getPassword();
        if(passwordEncoder.matches(actualPassword,org.getPassword())){
            org.setPassword(null);
            return org;
        }
        return null;
    }

    public UserRequestDTO userLogin(LoginRequestDTO request) throws IOException {
        String endPoint = dbUrl+"/db/api/user/get/"+request.getUsername();
        Object obj = dbApi.makeGetCall(endPoint,"",new HashMap<>());

        ObjectMapper objectMapper = new ObjectMapper();

        Map<String,Object> map = objectMapper.convertValue(obj,Map.class);
        Object ob = map.get("body");
        UserRequestDTO user = objectMapper.convertValue(ob, UserRequestDTO.class);

        String actualPassword = request.getPassword();
        if(passwordEncoder.matches(actualPassword,user.getPassword())){
            user.setPassword(null);
            return user;
        }
        return null;
    }


    public void inviteMember(String username, String inviteEmail,String role) {
        String endPoint = dbUrl+"/db/api/org/get";
        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("orgEmail", username);
        Object obj = dbApi.makeGetCall(endPoint,"",queryParams);
        ObjectMapper objectMapper = new ObjectMapper();

        Map<String,Object> map = objectMapper.convertValue(obj,Map.class);
        Object ob = map.get("body");
        OrgRequestDTO org = objectMapper.convertValue(ob, OrgRequestDTO.class);
        UUID orgId = org.getId();
        dbApi.createUser(inviteEmail,role,orgId);
        System.out.println("invited successfully");

    }
}
