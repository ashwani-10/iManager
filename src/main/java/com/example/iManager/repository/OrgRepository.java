package com.example.iManager.repository;

import com.example.iManager.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrgRepository extends JpaRepository<Organization, UUID> {
    Optional<Organization> findByEmail(String email);
}
