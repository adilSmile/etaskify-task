package com.etaskify.usermanager.service;


import java.util.UUID;

import com.etaskify.usermanager.data.request.UserRequest;

public interface MessageService {
    UUID getOrganizationIdByToken(String token);
    void sendUser(UserRequest userRequest);
}
