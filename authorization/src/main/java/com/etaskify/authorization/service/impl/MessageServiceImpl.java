package com.etaskify.authorization.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.Map;
import java.util.UUID;

import com.etaskify.authorization.data.request.UserData;
import com.etaskify.authorization.data.request.UserRequest;
import com.etaskify.authorization.entity.User;
import com.etaskify.authorization.security.jwt.JwtUtils;
import com.etaskify.authorization.service.MessageService;
import com.etaskify.authorization.service.UserService;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Log4j2
public class MessageServiceImpl implements MessageService {
  private JwtUtils jwtUtils;
  private UserService userService;

  private static final String TOKEN = "token";

  @Override
  @RabbitListener(queues = "${rabbitmq.auth.queue.name}")
  public UUID getUserIdFromToken(final Map<String, String> tokenMap) {
    log.info("User token received");
    if (!tokenMap.containsKey(TOKEN) || tokenMap.get(TOKEN).isEmpty()) {
      return null;
    }
    if (!jwtUtils.validateToken(tokenMap.get(TOKEN))) {
      return null;
    }
    String email = jwtUtils.extractUsername(tokenMap.get(TOKEN));
    User user = userService.getByEmail(email);
    log.info("Returning user ID");
    return user.getId();
  }

  @Override
  @RabbitListener(queues = "${rabbitmq.user.queue.name}")
  public Boolean verifyOrganizationUserList(final UserData userData) {
    return userService.verifyOrganizationUsers(userData);
  }

  @Override
  @RabbitListener(queues = "${rabbitmq.queue.name}")
  public void createNewUser(final UserRequest userRequest) {
    userService.create(userRequest);
  }
}
