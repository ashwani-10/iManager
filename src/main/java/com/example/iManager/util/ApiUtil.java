package com.example.iManager.util;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public interface ApiUtil {
    public ResponseEntity makePostCall(Object requestBody, String endPoint, String apiUrl,
                                       HashMap<String, String > queryParams);

    public Object makePutCall(Object requestBody, String endPoint, String apiUrl,
                              HashMap<String, String > queryParams);

    public Object makeGetCall(String endPoint, String apiUrl,
                              HashMap<String, String > queryParams);
}
