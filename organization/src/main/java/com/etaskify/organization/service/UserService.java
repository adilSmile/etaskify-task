package com.etaskify.organization.service;

import lombok.NonNull;

import com.etaskify.organization.data.request.AuthRequest;
import com.etaskify.organization.data.response.LoginResponse;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    LoginResponse authenticate(@NonNull AuthRequest authRequest);
}
