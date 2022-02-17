package com.etaskify.organization.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

import com.etaskify.organization.entity.Admin;
import com.etaskify.organization.entity.Organization;

public interface OrganizationRepository extends JpaRepository<Organization, UUID> {
    Boolean existsByNameContainingIgnoreCase(String name);
    Optional<Organization> findByAdmin(Admin admin);
}
