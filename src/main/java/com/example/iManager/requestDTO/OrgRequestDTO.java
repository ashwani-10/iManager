package com.example.iManager.requestDTO;

import com.example.iManager.enums.Role;
import com.example.iManager.enums.Subscription;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.NonNull;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrgRequestDTO {
    UUID id;
    String name;
    String email;
    String password;
    Role role;
    Subscription subscription;
    String amount;

    public OrgRequestDTO() {
    }

    public OrgRequestDTO(UUID id, String name, String email, String password,
                         Role role, Subscription subscription, String amount) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.subscription = subscription;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName( String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail( String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword( String password) {
        this.password = password;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription( Subscription subscription) {
        this.subscription = subscription;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
