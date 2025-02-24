package com.example.iManager.controller;

import com.example.iManager.requestDTO.OrgRequestDTO;
import com.example.iManager.service.OrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/org")
public class OrgController {

    @Autowired
    OrgService orgService;

    @PostMapping("/registration")
    public ResponseEntity orgRegistration(@RequestBody OrgRequestDTO request){
        try{
            orgService.orgRegistration(request);
            return new ResponseEntity<>("Org registered successfully", HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>("registration failed",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
