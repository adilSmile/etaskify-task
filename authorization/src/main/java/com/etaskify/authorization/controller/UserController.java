package com.etaskify.authorization.controller;

import lombok.AllArgsConstructor;

import com.etaskify.authorization.data.request.LoginRequest;
import com.etaskify.authorization.data.response.LoginResponse;
import com.etaskify.authorization.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
    return ResponseEntity.ok(userService.login(loginRequest));
  }
}
