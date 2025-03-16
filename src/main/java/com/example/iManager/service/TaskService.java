package com.example.iManager.service;

import com.example.iManager.requestDTO.TaskRequestDTO;
import com.example.iManager.util.DbApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    @Autowired
    DbApi dbApi;

    public void createTask(TaskRequestDTO taskRequestDTO){
        dbApi.createTask(taskRequestDTO);
    }
}
