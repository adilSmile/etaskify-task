package com.etaskify.task_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

import com.etaskify.task_manager.entity.Task;

public interface TaskRepository extends JpaRepository<Task, UUID> {
    List<Task> findAllByOrganizationId(UUID organizationId);
}
