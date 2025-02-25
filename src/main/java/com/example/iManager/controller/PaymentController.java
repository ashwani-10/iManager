package com.example.iManager.controller;

import com.example.iManager.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/payments")
@CrossOrigin(origins = {"http://localhost:63342","http://127.0.0.1:5500"})
public class PaymentController {

    @Autowired
    PaymentService paymentService;

//    @PostMapping("/create-order")
//    public String createOrder(@RequestParam int amount,
//                              @RequestParam String currency){
//        try {
//            return paymentService.createOrder(amount, currency, "receipt_100");
//        }catch (Exception e){
//            throw new RuntimeException("failed transaction");
//        }
//    }
}
