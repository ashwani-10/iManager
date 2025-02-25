package com.example.iManager.util;

import com.example.iManager.model.Organization;
import org.springframework.stereotype.Service;
import java.util.HashMap;

@Service
public class DbApi extends ApiUtilImpl{
    public void createOrg(Organization org ){
        System.out.println("hit kardiya ");
        try {
            String endpoint = "http://localhost:8082/db/api/org/registration";
            makePostCall(org, endpoint, "", new HashMap<>());
        }catch (Exception e){
            System.out.println("Fail ho gaya");
            throw new RuntimeException("Failed db api");
        }
    }
}
