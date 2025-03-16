package com.example.iManager.service;

import com.example.iManager.model.Project;
import com.example.iManager.requestDTO.ProjectRequestDTO;
import com.example.iManager.util.DbApi;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ProjectService {
    @Autowired
    DbApi dbApi;

    public Project createProject(ProjectRequestDTO requestDTO){
        Object object = dbApi.getOrg(requestDTO.getOrgId());
        if(object != null){
            Object projectOb = dbApi.createProject(requestDTO);
            ObjectMapper objectMapper = new ObjectMapper();

            Map<String,Object> map = objectMapper.convertValue(projectOb,Map.class);
            Object ob = map.get("body");
            Project project = objectMapper.convertValue(ob, Project.class);
            return project;
        }
        return null;
    }
}
