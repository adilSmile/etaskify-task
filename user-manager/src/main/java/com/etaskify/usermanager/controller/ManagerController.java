package com.etaskify.usermanager.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import com.etaskify.usermanager.data.request.NewUserRequest;
import com.etaskify.usermanager.service.ManagerService;

@RestController
@RequestMapping("/api")
public class ManagerController {

  private final ManagerService managerService;

  public ManagerController(final ManagerService managerService) {
    this.managerService = managerService;
  }

  @PostMapping("/createUser")
  public ResponseEntity<Void> createUser(
      @RequestAttribute UUID organizationId,
      @RequestBody NewUserRequest newUserRequest
  ) {
    managerService.createUser(newUserRequest, organizationId);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
