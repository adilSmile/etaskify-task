package com.etaskify.organization.service.impl;

import lombok.NonNull;

import com.etaskify.organization.data.request.OrganizationRequestBody;
import com.etaskify.organization.entity.Admin;
import com.etaskify.organization.entity.Organization;
import com.etaskify.organization.exception.AlreadyExistsException;
import com.etaskify.organization.repository.OrganizationRepository;
import com.etaskify.organization.service.AdminService;
import com.etaskify.organization.service.OrganizationService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final AdminService adminService;

    public OrganizationServiceImpl(final OrganizationRepository organizationRepository,
        final AdminService adminService) {
        this.organizationRepository = organizationRepository;
        this.adminService = adminService;
    }

    @Transactional
    @Override
    public void create(@NonNull OrganizationRequestBody organizationRequestBody) {
        checkOrganizationName(organizationRequestBody.getName());

        Admin admin = adminService.createAdmin(
                organizationRequestBody.getAdminUsername(),
                organizationRequestBody.getAdminEmail(),
                organizationRequestBody.getAdminPassword()
        );

        Organization organization = new Organization();
        organization.setName(organizationRequestBody.getName());
        organization.setPhone(organizationRequestBody.getPhone());
        organization.setAddress(organizationRequestBody.getPhone());
        organization.setAdmin(admin);

        organizationRepository.save(organization);
    }

    @Override
    public Organization getOrganizationByAdminUsername(@NonNull String adminUsername) {
        Admin admin = adminService.getAdminByUsername(adminUsername);
        return organizationRepository.findByAdmin(admin).orElse(null);
    }

    protected void checkOrganizationName(@NonNull String name) {
        if (organizationRepository.existsByNameContainingIgnoreCase(name)) {
            throw new AlreadyExistsException(String.format("Organization: %s already exists", name));
        }
    }
}
