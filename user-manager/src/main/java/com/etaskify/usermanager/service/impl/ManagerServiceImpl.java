package com.etaskify.usermanager.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import com.etaskify.usermanager.data.request.NewUserRequest;
import com.etaskify.usermanager.data.request.UserRequest;
import com.etaskify.usermanager.service.ManagerService;
import com.etaskify.usermanager.service.MessageService;

@Service
@AllArgsConstructor
public class ManagerServiceImpl implements ManagerService {

    private MessageService messageService;

    @Override
    public void createUser(NewUserRequest newUserRequest, UUID organizationId) {
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail(newUserRequest.getEmail());
        userRequest.setName(newUserRequest.getName());
        userRequest.setSurname(newUserRequest.getSurname());
        userRequest.setOrganizationId(organizationId);
        messageService.sendUser(userRequest);
    }
}
