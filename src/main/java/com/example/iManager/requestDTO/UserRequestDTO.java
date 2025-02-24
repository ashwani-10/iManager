package com.example.iManager.requestDTO;

import com.example.iManager.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    @NonNull
    Role role;
    @NonNull
    String OrgId;

}
