package com.etaskify.authorization.repository;

import java.util.Optional;
import java.util.UUID;
import com.etaskify.authorization.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {
  Boolean existsByEmailContainingIgnoreCase(String email);
  Optional<User> findByEmailContainingIgnoreCase(String email);
}
