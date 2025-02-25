package com.example.iManager.service;

import com.example.iManager.exceptions.EmailSendingFailedException;
import com.example.iManager.exceptions.RegistrationFailedException;
import com.example.iManager.kafkaProducerDTO.UserRegistrationMessageDTO;
import com.example.iManager.model.OTP;
import com.example.iManager.model.User;
import com.example.iManager.requestDTO.UserRequestDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final OtpService otpService;
    private final Mapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final KafkaProducerService kafkaProducer;

    public UserService(OtpService otpService, Mapper mapper,
                       PasswordEncoder passwordEncoder,
                       KafkaProducerService kafkaProducer) {
        this.otpService = otpService;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
        this.kafkaProducer = kafkaProducer;
    }

    public void userRegistration(UserRequestDTO request){
        try {
            User user  = mapper.userMapper(request);
            OTP otp = otpService.otpGenerator(request.getEmail());

//            userRepository.save(user);
//            otpRepository.save(otp);

            sendVerificationMail(user,otp.getOtp());
        } catch (Exception e) {
            throw new RegistrationFailedException("Failed to complete registration",e);
        }
    }

    private void sendVerificationMail(User user,String otp) {
        final String topic = "user-registration";
        UserRegistrationMessageDTO mailDTO = UserRegistrationMessageDTO.builder()
                .name(user.getName())
                .email(user.getEmail())
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

//    public void emailVerification(OTP credentials) {
//        OTP otp = otpRepository.findByEmail(credentials.getEmail());
//        if(!otp.getOtp().equals(credentials.getOtp())){
//            throw new RuntimeException("Wrong OTP ");
//        }
//        otpRepository.delete(otp);
//    }

//    public boolean userLogin(LoginRequestDTO request){
//        User user = userRepository.findByEmail(request.getUsername())
//                .orElseThrow(()-> new RuntimeException("User not found"));
//        String actualPassword = request.getPassword();
//        if(passwordEncoder.matches(actualPassword,user.getPassword())){
//            return true;
//        }
//        return false;
//    }

//    public void inviteMember(String username, String inviteEmail) {
//        User user = userRepository.findByEmail(username)
//                .orElseThrow(()-> new RuntimeException("User not found"));
//    }
}
