package com.etaskify.organization.service;

import lombok.NonNull;

import com.etaskify.organization.data.request.OrganizationRequestBody;
import com.etaskify.organization.entity.Organization;

public interface OrganizationService {
    void create(@NonNull OrganizationRequestBody organizationRequestBody);
    Organization getOrganizationByAdminUsername(@NonNull String adminUsername);
}
