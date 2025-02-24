package com.example.iManager.requestDTO;

import com.example.iManager.enums.Subscription;
import lombok.Data;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;

import java.util.UUID;

@Data
public class OrgRequestDTO {
    @NonNull
    String name;
    @NonNull
    String email;
    @NonNull
    String password;
    @NonNull
    Subscription subscription;

}
