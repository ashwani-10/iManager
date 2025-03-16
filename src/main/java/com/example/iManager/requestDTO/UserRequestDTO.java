package com.example.iManager.requestDTO;

import com.example.iManager.enums.Role;
import lombok.Data;
import lombok.NonNull;

import java.util.UUID;

@Data
public class UserRequestDTO {
    @NonNull
    String name;
    @NonNull
    String Email;
    @NonNull
    String password;

}
