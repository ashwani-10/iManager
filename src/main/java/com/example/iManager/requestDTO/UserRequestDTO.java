package com.example.iManager.requestDTO;

import com.example.iManager.enums.Role;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.UUID;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRequestDTO {
    UUID id;
    String name;
    String Email;
    String password;
    Role role;
    UUID orgId;
    String orgName;
    String logoUrl;

    public UserRequestDTO() {
    }

    public UserRequestDTO(UUID id, String name, String email, String password,
                          Role role, UUID orgId, String orgName, String logoUrl) {
        this.id = id;
        this.name = name;
        Email = email;
        this.password = password;
        this.role = role;
        this.orgId = orgId;
        this.orgName = orgName;
        this.logoUrl = logoUrl;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public UUID getOrgId() {
        return orgId;
    }

    public void setOrgId(UUID orgId) {
        this.orgId = orgId;
    }
}
