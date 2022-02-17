package com.etaskify.organization.service.impl;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import com.etaskify.organization.data.request.AuthRequest;
import com.etaskify.organization.data.response.LoginResponse;
import com.etaskify.organization.entity.Admin;
import com.etaskify.organization.security.jwt.JwtUtils;
import com.etaskify.organization.service.AdminService;
import com.etaskify.organization.service.UserService;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private AdminService adminService;
    private AuthenticationManager authenticationManager;
    private JwtUtils jwtUtils;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminService.getAdminByUsername(username);
        if (admin == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new User(admin.getUsername(), admin.getPassword(), new ArrayList<>());
    }

    @Override
    public LoginResponse authenticate(@NonNull AuthRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );
        UserDetails userDetails = loadUserByUsername(authRequest.getUsername());
        String accessToken = jwtUtils.generateToken(userDetails);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAccessToken(accessToken);
        return loginResponse;
    }
}
