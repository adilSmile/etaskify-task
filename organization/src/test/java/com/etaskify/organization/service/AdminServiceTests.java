package com.etaskify.organization.service;


import com.etaskify.organization.exception.AlreadyExistsException;
import com.etaskify.organization.exception.BadRequestException;
import com.etaskify.organization.repository.AdminRepository;
import com.etaskify.organization.service.impl.AdminServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminServiceTests {
    @Mock
    private AdminRepository adminRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AdminService adminService = new AdminServiceImpl(adminRepository, passwordEncoder);

    @Test
    void existingUsernameShouldThrowException() {
        when(adminRepository.existsByUsernameContainingIgnoreCase("admin")).thenReturn(true);
        Throwable exception = assertThrows(AlreadyExistsException.class, () -> adminService.checkUsername("admin"));
        assertEquals("Admin with username: admin already exists", exception.getMessage());
    }

    @Test
    void badEmailPatternShouldThrowException() {
        Throwable exception = assertThrows(BadRequestException.class, () -> adminService.checkEmail("admin"));
        assertEquals("Invalid email pattern", exception.getMessage());
    }

}
