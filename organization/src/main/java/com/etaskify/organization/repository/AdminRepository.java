package com.etaskify.organization.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

import com.etaskify.organization.entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, UUID> {

    Boolean existsByUsernameContainingIgnoreCase(String username);

    Boolean existsByEmailContainingIgnoreCase(String email);

    Optional<Admin> findByUsername(String username);
}
