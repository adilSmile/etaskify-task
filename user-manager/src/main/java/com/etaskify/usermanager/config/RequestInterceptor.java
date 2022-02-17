package com.etaskify.usermanager.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

import com.etaskify.usermanager.exception.AccessDeniedException;
import com.etaskify.usermanager.service.MessageService;

@Component
public class RequestInterceptor implements HandlerInterceptor {

  private final MessageService messageService;

  public RequestInterceptor(final MessageService messageService) {
    this.messageService = messageService;
  }

  private static final String AUTHORIZATION = "Authorization";
  private static final String BEARER = "Bearer";
  private static final String USER_ID = "userId";
  private static final int TOKEN_SUBSTRING = 7;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
    String headerString = request.getHeader(AUTHORIZATION);
    if (headerString == null || !headerString.contains(BEARER)) {
      throw new AccessDeniedException();
    }
    String token = headerString.substring(TOKEN_SUBSTRING);
    UUID userId = messageService.getOrganizationIdByToken(token);
    if (userId == null) {
      throw new AccessDeniedException();
    }
    request.setAttribute(USER_ID, userId);
    return true;
  }
}
