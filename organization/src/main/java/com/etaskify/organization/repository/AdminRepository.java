package com.etaskify.organization.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

import com.etaskify.organization.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, UUID> {
    Boolean existsByUsernameContainingIgnoreCase(String username);
    Boolean existsByEmailContainingIgnoreCase(String email);

    Optional<Admin> findByUsername(String username);
}
