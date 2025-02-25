package com.example.iManager.util;

import org.apache.kafka.common.protocol.types.Field;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class PaymentApi extends ApiUtilImpl{
    public ResponseEntity payment(int amount,String currency){
        String endpoint = "http://localhost:8083/api/payments/create-order";
        HashMap<String, String> params = new HashMap<>();
        params.put("amount",""+amount);
        params.put("currency",currency);
        System.out.println("payment api hiting");
        return makePostCall(null,endpoint,"",params);
    }

}
