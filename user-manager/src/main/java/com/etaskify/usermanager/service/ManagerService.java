package com.etaskify.usermanager.service;


import java.util.UUID;

import com.etaskify.usermanager.data.request.NewUserRequest;

public interface ManagerService {
    void createUser(NewUserRequest newUserRequest, UUID organizationId);
}
