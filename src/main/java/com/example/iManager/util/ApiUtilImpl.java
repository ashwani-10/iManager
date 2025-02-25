package com.example.iManager.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;

@Component
public class ApiUtilImpl implements ApiUtil{
    @Autowired
    RestTemplate restTemplate;


    @Override
    public ResponseEntity makePostCall(Object requestBody, String endPoint, String apiUrl, HashMap<String, String > queryParams) {
        String url = endPoint;
        url = addQueryParams(url, queryParams);
        URI finalUrl = URI.create(url);
        RequestEntity req = RequestEntity.post(finalUrl).body(requestBody);
        try{
            ResponseEntity<String> resp = restTemplate.exchange(finalUrl, HttpMethod.POST, req, String.class);
            return resp;
        }catch (Exception e){
            throw e;
        }
    }

    public String addQueryParams(String url, HashMap<String, String> queryParams){
        if(queryParams.size() > 0) {
            url += "?";
            int size = 1;
            for(String key : queryParams.keySet()){
                String value = queryParams.get(key);
                if(size == queryParams.size()){
                    url += key + "=" +value;
                }else{
                    url += key + "=" +value + "&";
                }
                size++;
            }
        }
        return url;
    }

    @Override
    public Object makePutCall(Object requestBody, String endPoint, String apiUrl, HashMap<String, String > queryParams){
        String url = apiUrl + "/" + endPoint;

        url = addQueryParams(url, queryParams);

        URI finalURL = URI.create(url);

        RequestEntity request = RequestEntity.put(finalURL).body(requestBody);

        RestTemplate restTemplate = new RestTemplate();

        try{
            ResponseEntity<Object> resp  = restTemplate.exchange(finalURL, HttpMethod.PUT, request, Object.class);
            return resp.getBody();
        }catch (Exception e){
            throw e;
        }

    }

    @Override
    public Object makeGetCall(String endPoint, String apiUrl, HashMap<String, String> queryParams) {
        String url = endPoint;

        url = addQueryParams(url, queryParams);

        URI finalURL = URI.create(url);
        RequestEntity request = RequestEntity.get(finalURL).build();
        try {
            ResponseEntity<Object> resp  = restTemplate.exchange(url, HttpMethod.GET, request, Object.class);
            return resp.getBody();
        }catch (Exception e){
            throw e;
        }

    }

}
