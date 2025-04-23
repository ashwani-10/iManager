package com.example.iManager.util;

import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentApi extends ApiUtilImpl{
    @Value("${service.payment.url}")
    private String paymentUrl;

    public ResponseEntity payment(int amount,String currency){
        String endpoint = paymentUrl+"/api/payments/create-order";
        HashMap<String, String> params = new HashMap<>();
        params.put("amount",""+amount);
        params.put("currency",currency);
        System.out.println("payment api hiting");
        return this.makePostCall(null,endpoint,"",params);
    }

    @Override
    public ResponseEntity<Object> makePostCall(Object requestBody, String endPoint, String apiUrl, HashMap<String, String> queryParams) {
        String url = this.addQueryParams(endPoint,queryParams);
        URI finalUrl = URI.create(url);
        RequestEntity req = RequestEntity.post(finalUrl).body(requestBody);
        try{
            ResponseEntity<Object> resp = restTemplate.exchange(finalUrl, HttpMethod.POST, req, Object.class);
            return resp;
        }catch (Exception e){
            throw e;
        }
    }
}
