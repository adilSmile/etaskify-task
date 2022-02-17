package com.etaskify.task_manager.service;

import java.util.List;
import java.util.UUID;

import com.etaskify.task_manager.data.request.TaskRequest;
import com.etaskify.task_manager.entity.Task;

public interface TaskService {
    void create(TaskRequest taskRequest, UUID creatorId);
    List<Task> getOrganizationTasks(UUID organizationId, UUID userId);
}
