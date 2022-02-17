package com.etaskify.task_manager.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import com.etaskify.task_manager.data.request.TaskRequest;
import com.etaskify.task_manager.entity.Task;
import com.etaskify.task_manager.service.TaskService;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(final TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    ResponseEntity<Void> create(@RequestAttribute UUID userId, @RequestBody TaskRequest taskRequest){
        taskService.create(taskRequest, userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{organizationId}/tasks")
    ResponseEntity<List<Task>> getAllOrganizationTasks(@PathVariable UUID organizationId, @RequestAttribute UUID userId){
        return ResponseEntity.ok(taskService.getOrganizationTasks(organizationId, userId));
    }
}
