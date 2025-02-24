package com.example.iManager.model;

import jakarta.persistence.*;
import lombok.Data;
import org.aspectj.weaver.ast.Or;

import java.util.List;

@Entity
@Data
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @OneToMany(mappedBy = "project")
    private List<Tasks> tasks;

    //add users in the project
    @ManyToMany
    @JoinTable(name = "members",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> members;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

}
