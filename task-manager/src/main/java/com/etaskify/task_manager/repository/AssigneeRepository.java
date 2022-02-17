package com.etaskify.task_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

import com.etaskify.task_manager.entity.Assignee;

public interface AssigneeRepository extends JpaRepository<Assignee, UUID>  {
}
