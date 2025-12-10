package com.example.iManager.controller;

import com.example.iManager.jwtconfig.JwtUtil;
import com.example.iManager.model.Payment;
import com.example.iManager.requestDTO.LoginRequestDTO;
import com.example.iManager.requestDTO.OrgRequestDTO;
import com.example.iManager.requestDTO.UserRequestDTO;
import com.example.iManager.service.Mapper;
import com.example.iManager.service.OrgService;
import com.example.iManager.util.DbApi;
import com.example.iManager.util.PaymentApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/org")
public class OrgController {

    @Autowired
    OrgService orgService;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    PaymentApi paymentApi;
    @Autowired
    DbApi dbApi;
    @Autowired
    Mapper mapper;

    @PostMapping("/registration")
    public ResponseEntity orgRegistration(@RequestBody OrgRequestDTO request,
                                          @RequestParam int amount,
                                          @RequestParam String currency){
        try{
            ResponseEntity<Map<String,Object>> res = paymentApi.payment(amount,currency);
            Map<String,Object> responseBody = res.getBody();

            System.out.println("Map Create ho gaya hai");

            if(responseBody == null || responseBody.get("id") == null){
                System.out.println("error agya payment order nhi create hua");
                return new ResponseEntity("Payment Order Creation failed",HttpStatus.BAD_REQUEST);
            }

            String orderId = (String) responseBody.get("id");
            Payment payment = mapper.paymentMapper(request);
            payment.setOrderId(orderId);
            System.out.println("payment object bhej dia api mei");

            dbApi.createPendingPayment(payment);
            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>("Payment Order Creation Failed",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity userLogin(@RequestBody LoginRequestDTO request) throws IOException {
        System.out.println("login method hit hua hh");
        OrgRequestDTO requestDTO = orgService.orgLogin(request);
        UserRequestDTO userRequestDTO = null;
        if (requestDTO == null){
            userRequestDTO = orgService.userLogin(request);
        }
        try {
            String token = jwtUtil.generateToken(request.getUsername());
            System.out.println(token);
            Map<String, Object> details = new HashMap<>();
            details.put("token", token);
            if (requestDTO != null) {
                details.put("data", requestDTO);
            } else {
                details.put("data", userRequestDTO);
            }
            return new ResponseEntity(details, HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity("Invalid Creds", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/test")
    public String test(@RequestHeader("Authorization") String authHeader){
        String token = authHeader.substring(7);
        System.out.println(token);
        String username = jwtUtil.extractUsername(token);
        System.out.println(username);
        System.out.println("endpoint hi hua");
        if(jwtUtil.validateToken(token,username)){
            return username + "testing verified";
        }
        return "testing failed";
    }

    @PostMapping("/invite")
    public ResponseEntity inviteMember(@RequestHeader("Authorization") String authHeader,
                                       @RequestParam String inviteEmail,
                                       @RequestParam String role){
        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);

        if(jwtUtil.validateToken(token,username)) {
            try {
                orgService.inviteMember(username,inviteEmail,role);
                return new ResponseEntity("Invited Successfully",HttpStatus.OK);
            }catch (Exception e){
                return new ResponseEntity("Inviting user failed",HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity("Not a valid user",HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/docker")
    public String dockerTest(){
        dbApi.checkDocker();
        return "Docker Connected with main app";
    }
}
