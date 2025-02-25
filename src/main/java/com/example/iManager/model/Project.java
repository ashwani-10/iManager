package com.example.iManager.model;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class Project {
    private Long id;

    private String name;

    private Organization organization;

    private List<Tasks> tasks;

    private List<User> members;

    private User createdBy;

}
