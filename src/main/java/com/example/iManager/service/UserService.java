package com.example.iManager.service;

import com.example.iManager.model.User;
import com.example.iManager.repository.UserRepository;
import com.example.iManager.requestDTO.UserRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserMapper userMapper;


    public void userRegistration(UserRequestDTO userRequestDTO){
        User user= userMapper.userMapper(userRequestDTO);
        userRepository.save(user);
    }
}
