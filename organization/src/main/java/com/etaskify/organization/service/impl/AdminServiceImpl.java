package com.etaskify.organization.service.impl;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.etaskify.organization.entity.Admin;
import com.etaskify.organization.exception.AlreadyExistsException;
import com.etaskify.organization.exception.BadRequestException;
import com.etaskify.organization.repository.AdminRepository;
import com.etaskify.organization.service.AdminService;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {
    private AdminRepository adminRepository;
    private PasswordEncoder passwordEncoder;

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,}$";

    @Override
    public Admin createAdmin(@NonNull String username, @NonNull String email,@NonNull String password) {
        checkUsername(username);
        checkEmail(email);
        checkPassword(password);

        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setEmail(email);
        admin.setPassword(passwordEncoder.encode(password));

        return adminRepository.save(admin);
    }

    @Override
    public Admin getAdminByUsername(@NonNull String username) {
        return adminRepository.findByUsername(username).orElse(null);
    }

    public void checkUsername(@NonNull String username){
        Boolean isUsernameOccupied = adminRepository.existsByUsernameContainingIgnoreCase(username);
        if (isUsernameOccupied){
            throw new AlreadyExistsException(String.format("Admin with username: %s already exists", username));
        }
    }

    public void checkEmail(@NonNull String email){
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()){
            throw new BadRequestException("Invalid email pattern");
        }

        Boolean isEmailOccupied = adminRepository.existsByEmailContainingIgnoreCase(email);
        if (isEmailOccupied){
            throw new AlreadyExistsException(String.format("Admin with email: %s already exists", email));
        }
    }

    public void checkPassword(@NonNull String password){
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        if (!matcher.matches()){
            throw new BadRequestException("Invalid password pattern");
        }
    }
}
