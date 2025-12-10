package com.example.iManager.controller;

import com.example.iManager.jwtconfig.JwtUtil;
import com.example.iManager.requestDTO.TaskRequestDTO;
import com.example.iManager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/task")
public class TaskController {
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    TaskService taskService;

    @PostMapping("/create")
    public void createTask(@RequestHeader("Authorization") String authHeader,
                           @RequestBody TaskRequestDTO taskRequestDTO){
        String token = authHeader.substring(7);
        System.out.println(token);
        String username = jwtUtil.extractUsername(token);
        System.out.println(username);
        System.out.println("endpoint hit hua");
        if(jwtUtil.validateToken(token,username)){
            taskService.createTask(taskRequestDTO);
            System.out.println("created task");
        }else{
            System.out.println("failed creating task");
        }
    }
}
