package com.etaskify.organization.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

import com.etaskify.organization.entity.Organization;
import com.etaskify.organization.security.jwt.JwtUtils;
import com.etaskify.organization.service.MessageService;
import com.etaskify.organization.service.OrganizationService;

@Service
@AllArgsConstructor
@Log4j2
public class MessageServiceImpl implements MessageService {

    private JwtUtils jwtUtils;
    private OrganizationService organizationService;

    @Override
    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public UUID getOrganizationIdFromReceivedToken(String token) {
        log.info("Received token");
        if (!jwtUtils.validateToken(token)) {
            return null;
        }

        String adminUsername = jwtUtils.extractUsername(token);
        Organization organization = organizationService.getOrganizationByAdminUsername(adminUsername);
        log.info("Returning organization id");
        return organization.getId();
    }
}
