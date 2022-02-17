package com.etaskify.authorization.service.impl;

import lombok.NonNull;

import java.util.ArrayList;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.etaskify.authorization.data.request.LoginRequest;
import com.etaskify.authorization.data.request.UserData;
import com.etaskify.authorization.data.request.UserRequest;
import com.etaskify.authorization.data.response.LoginResponse;
import com.etaskify.authorization.entity.User;
import com.etaskify.authorization.exception.AlreadyExistsException;
import com.etaskify.authorization.exception.BadRequestException;
import com.etaskify.authorization.repository.UserRepository;
import com.etaskify.authorization.security.jwt.JwtUtils;
import com.etaskify.authorization.service.UserService;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final AuthenticationManager authenticationManager;
  private final JwtUtils jwtUtils;
  private final PasswordEncoder passwordEncoder;

  public UserServiceImpl(final UserRepository userRepository,
      final AuthenticationManager authenticationManager, final JwtUtils jwtUtils,
      final PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.authenticationManager = authenticationManager;
    this.jwtUtils = jwtUtils;
    this.passwordEncoder = passwordEncoder;
  }

  private static final String DEFAULT_PASSWORD = "Aa123456";
  private static final String EMAIL_PATTERN = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";

  @Override
  public void create(@NonNull UserRequest userRequest) {
    checkEmail(userRequest.getEmail());

    User user = new User();
    user.setEmail(userRequest.getEmail());
    user.setPassword(passwordEncoder.encode(DEFAULT_PASSWORD));
    user.setName(userRequest.getName());
    user.setSurname(userRequest.getSurname());
    user.setOrganizationId(userRequest.getOrganizationId());

    userRepository.save(user);
  }

  @Override
  public User getByEmail(@NonNull String email) {
    return userRepository.findByEmailContainingIgnoreCase(email).orElse(null);
  }

  @Override
  public Boolean verifyOrganizationUsers(@NonNull UserData userData) {
    User creator = userRepository.findById(userData.getCreatorId()).orElse(null);
    if (creator == null) {
      return null;
    }
    for (UUID userId : userData.getAssignees()) {
      User assignee = userRepository.findById(userId).orElse(null);
      if (assignee == null || !assignee.getOrganizationId().equals(creator.getOrganizationId())) {
        return false;
      }
    }
    return true;
  }

  @Override
  public LoginResponse login(@NonNull LoginRequest loginRequest) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
    );
    UserDetails userDetails = loadUserByUsername(loginRequest.getEmail());
    String accessToken = jwtUtils.generateToken(userDetails);
    return new LoginResponse(accessToken);
  }

  @Override
  public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
    User user = getByEmail(username);
    if (user == null) {
      throw new UsernameNotFoundException(String.format("User with email: %s not found", username));
    }
    return new org.springframework.security.core.userdetails.User(
        user.getEmail(),
        user.getPassword(),
        new ArrayList<>()
    );
  }

  private void checkEmail(@NonNull String email) {

    Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    Matcher matcher = pattern.matcher(email);
    if (!matcher.matches()) {
      throw new BadRequestException("Invalid email pattern");
    }

    boolean isEmailOccupied = userRepository.existsByEmailContainingIgnoreCase(email);
    if (isEmailOccupied) {
      throw new AlreadyExistsException(String.format("Admin with email: %s already exists", email));
    }
  }
}
