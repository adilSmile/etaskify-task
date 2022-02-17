package com.etaskify.organization.controller;

import lombok.AllArgsConstructor;

import com.etaskify.organization.data.request.AuthRequest;
import com.etaskify.organization.data.response.LoginResponse;
import com.etaskify.organization.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {
    private UserService userService;

    @PostMapping("/login")
    ResponseEntity<LoginResponse> login(@RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok(userService.authenticate(authRequest));
    }
}
