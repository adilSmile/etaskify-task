package com.etaskify.organization.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

import com.etaskify.organization.entity.Admin;
import com.etaskify.organization.entity.Organization;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, UUID> {

    Boolean existsByNameContainingIgnoreCase(String name);
    Optional<Organization> findByAdmin(Admin admin);
}
