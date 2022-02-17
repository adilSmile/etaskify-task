package com.etaskify.organization.service;

import lombok.NonNull;

import com.etaskify.organization.entity.Admin;

public interface AdminService {
    Admin createAdmin(@NonNull String username,@NonNull String email, @NonNull String password);
    Admin getAdminByUsername(@NonNull String username);
    void checkUsername(@NonNull String username);
    void checkEmail(@NonNull String email);
}
