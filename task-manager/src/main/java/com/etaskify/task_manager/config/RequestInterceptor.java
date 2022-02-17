package com.etaskify.task_manager.config;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

import com.etaskify.task_manager.exception.AccessDeniedException;
import com.etaskify.task_manager.service.MessageService;

@Component
@AllArgsConstructor
public class RequestInterceptor implements HandlerInterceptor {
    private MessageService messageService;

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
        UUID userId = messageService.getUserIdByToken(token);
        if (userId == null) {
            throw new AccessDeniedException();
        }
        request.setAttribute(USER_ID, userId);
        return true;
    }
}
