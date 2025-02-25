package com.example.iManager.controller;

import com.example.iManager.jwtconfig.JwtUtil;
import com.example.iManager.requestDTO.LoginRequestDTO;
import com.example.iManager.requestDTO.OrgRequestDTO;
import com.example.iManager.service.OrgService;
import com.example.iManager.util.PaymentApi;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/org")
@CrossOrigin(origins = {"http://localhost:63342","http://127.0.0.1:5500"})
public class OrgController {

    @Autowired
    OrgService orgService;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    PaymentApi paymentApi;

    @PostMapping("/registration")
    public ResponseEntity orgRegistration(@RequestBody OrgRequestDTO request,
                                          @RequestParam int amount,
                                          @RequestParam String currency){
        try{
            ResponseEntity res = paymentApi.payment(amount,currency);
            orgService.orgRegistration(request);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>("registration failed",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

        @PostMapping("/login")
    public ResponseEntity userLogin(@RequestBody LoginRequestDTO request) throws IOException {

        if(orgService.userLogin(request)){
            String token = jwtUtil.generateToken(request.getUsername());
            System.out.println(token);
            return new ResponseEntity(token,HttpStatus.OK);
        }
        return new ResponseEntity("Invalid Creds",HttpStatus.BAD_REQUEST);
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
}
