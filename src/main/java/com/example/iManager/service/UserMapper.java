package com.example.iManager.service;

import com.example.iManager.model.User;
import com.example.iManager.requestDTO.UserRequestDTO;
import org.apache.catalina.mapper.Mapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.Mapping;


@Service
public class UserMapper {

    public  User userMapper(UserRequestDTO userRequestDTO){
        User user=new User();
        user.setEmail(userRequestDTO.getEmail());
        user.setName(userRequestDTO.getName());
        user.setPassword(userRequestDTO.getPassword());

        return user;
    }
}
