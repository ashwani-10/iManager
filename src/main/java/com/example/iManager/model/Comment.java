package com.example.iManager.model;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Comment {
    UUID id;
    String message;
    User user;
}
