package com.example.iManager.model;

import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.util.UUID;

@Entity
@Table(name ="users")
public class User {
    @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;
    @Column(nullable = false,unique = true)
    String name;
    @Column(nullable = false ,unique = true)
    String Email;
    @Column(nullable = false)
    String password;

    public User(UUID id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        Email = email;
        this.password = password;
    }

    public User() {
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

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", Email='" + Email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
