package com.etaskify.authorization.service;

import com.etaskify.authorization.data.request.LoginRequest;
import com.etaskify.authorization.data.request.UserData;
import com.etaskify.authorization.data.request.UserRequest;
import com.etaskify.authorization.data.response.LoginResponse;
import com.etaskify.authorization.entity.User;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
  void create(UserRequest userRequest);
  User getByEmail(String email);
  Boolean verifyOrganizationUsers(UserData userData);
  LoginResponse login(LoginRequest loginRequest);
}
