package com.example.iManager.requestDTO;

import com.example.iManager.enums.Priority;
import com.example.iManager.enums.TaskStatus;

import java.util.UUID;

public class TaskRequestDTO {
    private String title;

    private String description;

    private TaskStatus status;

    private Priority priority;

    private UUID projectID;

    public TaskRequestDTO() {
    }

    public TaskRequestDTO(String title, String description, TaskStatus status,
                          Priority priority, UUID projectID) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.projectID = projectID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public UUID getProjectID() {
        return projectID;
    }

    public void setProjectID(UUID projectID) {
        this.projectID = projectID;
    }
}
