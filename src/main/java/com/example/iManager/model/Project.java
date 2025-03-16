package com.example.iManager.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class Project {

    private UUID id;

    private String name;

    private Organization organization;

    private List<SubProject> subProjects = new ArrayList<>();

    //add users in the project
    private List<User> members;

    public Project(UUID id, String name, Organization organization,
                   List<SubProject> subProjects, List<User> members) {
        this.id = id;
        this.name = name;
        this.organization = organization;
        this.subProjects = subProjects;
        this.members = members;
    }

    public Project() {
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

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public List<SubProject> getSubProjects() {
        return subProjects;
    }

    public void setSubProjects(List<SubProject> subProjects) {
        this.subProjects = subProjects;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }
}
