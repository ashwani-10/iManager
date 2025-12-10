package com.example.iManager.controller;

import com.example.iManager.jwtconfig.JwtUtil;
import com.example.iManager.model.Project;
import com.example.iManager.requestDTO.ProjectRequestDTO;
import com.example.iManager.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/project")
public class ProjectController {
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    ProjectService projectService;

    @PostMapping("/create")
    public ResponseEntity createProject(@RequestHeader("Authorization") String authHeader,
                                        @RequestBody ProjectRequestDTO projectRequestDTO){
        String token = authHeader.substring(7);
        System.out.println(token);
        String username = jwtUtil.extractUsername(token);
        System.out.println(username);
        System.out.println("endpoint hit hua");
        if(jwtUtil.validateToken(token,username)){
            Project project = projectService.createProject(projectRequestDTO);
            System.out.println("created project");
            return new ResponseEntity(project,HttpStatus.CREATED);
        }else{
            System.out.println("Failed creating project");
            return new ResponseEntity("Failed creating project",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
