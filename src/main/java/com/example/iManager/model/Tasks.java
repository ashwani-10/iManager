package com.example.iManager.model;

import com.example.iManager.enums.Priority;
import com.example.iManager.enums.TaskStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Tasks {
    private Long id;
    private String title;

    private String description;
    private TaskStatus status;

    private Priority priority;

    private Project project;

    private User assignedTo;

    private User createdBy;

}
