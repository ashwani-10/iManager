package com.example.iManager.util;

import com.example.iManager.model.Organization;
import com.example.iManager.model.Payment;
import com.example.iManager.requestDTO.ProjectRequestDTO;
import com.example.iManager.requestDTO.TaskRequestDTO;
import com.example.iManager.requestDTO.UserRequestDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.UUID;

@Service
public class DbApi extends ApiUtilImpl{
    @Value("${service.db.url}")
    private String dbUrl;

    public void createOrg(Organization org ){
        System.out.println("hit kardiya ");
        try {
            String endpoint = dbUrl+"/db/api/org/registration";
            makePostCall(org, endpoint, "", new HashMap<>());
        }catch (Exception e){
            System.out.println("Fail ho gaya");
            throw new RuntimeException("Failed db api");
        }
    }

    public Object getOrg(UUID orgId){
        System.out.println("hit kardiya ");
        try {
            String endpoint = dbUrl+"/db/api/org/get/"+orgId;
            Object object = makeGetCall( endpoint, "", new HashMap<>());
            return object;
        }catch (Exception e){
            System.out.println("Fail ho gaya");
            throw new RuntimeException("Failed db api");
        }
    }

    public void createPendingPayment(Payment payment){
        System.out.println("payment api hit kardiya ");
        try {
            String endpoint = dbUrl+"/db/api/payment/request";
            RequestEntity request = RequestEntity.post(endpoint).body(payment);
            ResponseEntity<String> response = restTemplate.exchange(endpoint, HttpMethod.POST, request, String.class);
            System.out.println("Succesfull payment saved");
        }catch (Exception e){
            System.out.println("Fail ho gaya");
            throw new RuntimeException("Failed db api");
        }
    }

    public void checkDocker() {
        System.out.println("in checkDocker method");
        try{
            String endpoint = dbUrl+"/db/api/org/docker";
            makeGetCall(endpoint,"",new HashMap<>());
            System.out.println("Kuch response mila hai");
        }catch (Exception e){
            System.out.println("Failed calling db docker");
        }
    }

    public void createUser(String userEmail, String role, UUID orgId){
        String endpoint = dbUrl+"/db/api/user/create";
        HashMap<String, String> params = new HashMap<>();
        params.put("userEmail",userEmail);
        params.put("userRole",role);
        params.put("orgId",""+orgId);
        String url = addQueryParams(endpoint,params);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, null, String.class);
        System.out.println(response);
    }

    public void finalizeUser(UserRequestDTO userRequestDTO){
        String endpoint = dbUrl+"/db/api/user/finalize";
        RequestEntity request = RequestEntity.post(endpoint).body(userRequestDTO);
        ResponseEntity<String> response = restTemplate.exchange(endpoint, HttpMethod.POST, request, String.class);
        System.out.println(response);
    }

    public void createTask(TaskRequestDTO taskRequestDTO){
        System.out.println("Inside task creating method");
        try {
            String endpoint = dbUrl+"/db/api/task/create";
            makePostCall(taskRequestDTO,endpoint,"",new HashMap<>());
            System.out.println("posted task successfully");
        }catch (Exception e){
            System.out.println("failed posting task");
        }
    }

    public Object createProject(ProjectRequestDTO request){
        System.out.println("Inside task creating method");
        try {
            String endpoint = dbUrl+"/db/api/project/create";
            Object object = makePostCall(request,endpoint,"",new HashMap<>());
            System.out.println("posted project successfully");
            return object;
        }catch (Exception e){
            System.out.println("failed posting project");
            throw new RuntimeException("failed posting project");
        }
    }
}
