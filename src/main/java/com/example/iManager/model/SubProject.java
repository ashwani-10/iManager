package com.example.iManager.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class SubProject {

    private UUID id;

    private String name;

    private Project project;

    private List<Tasks> tasks = new ArrayList<>();

    public SubProject() {
    }

    public SubProject(UUID id, String name, Project project, List<Tasks> tasks) {
        this.id = id;
        this.name = name;
        this.project = project;
        this.tasks = tasks;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<Tasks> getTasks() {
        return tasks;
    }

    public void setTasks(List<Tasks> tasks) {
        this.tasks = tasks;
    }
}
