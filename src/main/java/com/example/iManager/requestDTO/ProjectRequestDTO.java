package com.example.iManager.requestDTO;

import java.util.UUID;

public class ProjectRequestDTO {
    private String name;
    private UUID orgId;

    public ProjectRequestDTO(String name, UUID orgId) {
        this.name = name;
        this.orgId = orgId;
    }

    public ProjectRequestDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getOrgId() {
        return orgId;
    }

    public void setOrgId(UUID orgId) {
        this.orgId = orgId;
    }
}
