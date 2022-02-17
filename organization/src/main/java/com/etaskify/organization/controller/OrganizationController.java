package com.etaskify.organization.controller;

import lombok.AllArgsConstructor;

import com.etaskify.organization.data.request.OrganizationRequestBody;
import com.etaskify.organization.service.OrganizationService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/admin/organization")
public class OrganizationController {
    private OrganizationService organizationService;

    @PostMapping()
    public ResponseEntity<Void> create(@RequestBody OrganizationRequestBody organizationRequestBody){
        organizationService.create(organizationRequestBody);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
