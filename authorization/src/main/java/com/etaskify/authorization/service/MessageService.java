package com.etaskify.authorization.service;

import java.util.Map;
import java.util.UUID;

import com.etaskify.authorization.data.request.UserData;
import com.etaskify.authorization.data.request.UserRequest;

public interface MessageService {

  UUID getUserIdFromToken(Map<String, String> tokenMap);
  Boolean verifyOrganizationUserList(UserData userData);
  void createNewUser(UserRequest userRequest);
}
