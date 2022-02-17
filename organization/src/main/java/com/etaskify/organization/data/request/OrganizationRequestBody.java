package com.etaskify.organization.data.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationRequestBody {
    private String name;
    private String phone;
    private String address;
    private String adminUsername;
    private String adminEmail;
    private String adminPassword;
}
