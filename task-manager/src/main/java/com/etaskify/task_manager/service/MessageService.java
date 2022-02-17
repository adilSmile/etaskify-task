package com.etaskify.task_manager.service;

import java.util.UUID;

import com.etaskify.task_manager.data.request.UserData;

public interface MessageService {
    UUID getUserIdByToken(String token);
    Boolean verifyUserSet(UserData userData);
}
