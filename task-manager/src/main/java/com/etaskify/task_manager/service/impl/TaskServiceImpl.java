package com.etaskify.task_manager.service.impl;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.etaskify.task_manager.data.request.TaskRequest;
import com.etaskify.task_manager.data.request.UserData;
import com.etaskify.task_manager.entity.Assignee;
import com.etaskify.task_manager.entity.Task;
import com.etaskify.task_manager.exception.BadRequestException;
import com.etaskify.task_manager.repository.AssigneeRepository;
import com.etaskify.task_manager.repository.TaskRepository;
import com.etaskify.task_manager.service.MessageService;
import com.etaskify.task_manager.service.TaskService;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {
    private MessageService messageService;
    private TaskRepository taskRepository;
    private AssigneeRepository assigneeRepository;

    @Override
    @Transactional
    public void create(@NonNull TaskRequest taskRequest, @NonNull UUID creatorId) {
        checkAssignees(taskRequest.getAssignees(), creatorId);

        Task task = new Task();
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setStatus(taskRequest.getStatus());
        task.setDeadline(OffsetDateTime.of(taskRequest.getDeadline(), LocalTime.now(), ZoneOffset.UTC));
        task.setCreatedBy(creatorId);
        task.setOrganizationId(taskRequest.getOrganizationId());

        Task savedTask = taskRepository.save(task);

        for (UUID assignees : taskRequest.getAssignees()) {
            Assignee assignee = new Assignee();
            assignee.setTask(savedTask);
            assignee.setId(assignees);
            assigneeRepository.save(assignee);
        }
    }

    @Override
    public List<Task> getOrganizationTasks(@NonNull UUID organizationId, @NonNull UUID userId) {
        return taskRepository.findAllByOrganizationId(organizationId);
    }

    void checkAssignees(Set<UUID> assignees, UUID creatorId) {
        if (assignees == null || assignees.isEmpty()) {
            throw new BadRequestException("Assignees are required");
        }

        UserData userData = new UserData();
        userData.setAssignees(assignees);
        userData.setCreatorId(creatorId);

        if (!messageService.verifyUserSet(userData)) {
            throw new BadRequestException("Assignees are not valid");
        }
    }
}
