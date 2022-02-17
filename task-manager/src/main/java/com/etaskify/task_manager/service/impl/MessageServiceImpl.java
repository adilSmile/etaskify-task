package com.etaskify.task_manager.service.impl;

import com.etaskify.task_manager.data.request.UserData;
import com.etaskify.task_manager.service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class MessageServiceImpl implements MessageService {

    private final RabbitTemplate rabbitTemplate;

    private final ObjectMapper objectMapper;

    @Qualifier("authExchange")
    private final DirectExchange authExchange;

    @Qualifier("usersExchange")
    private final DirectExchange usersExchange;

    public MessageServiceImpl(final RabbitTemplate rabbitTemplate, final ObjectMapper objectMapper,
        final DirectExchange authExchange, final DirectExchange usersExchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
        this.authExchange = authExchange;
        this.usersExchange = usersExchange;
    }

    @Override
    public UUID getUserIdByToken(@NonNull String token) {
        Map<String, String> map = Map.of("token", token);
        Object response = rabbitTemplate.convertSendAndReceive(authExchange.getName(),"authkey", map);
        if (response == null) return null;
        return (UUID) response;
    }

    @Override
    public Boolean verifyUserSet(@NonNull UserData userData) {
        try {
            String usersString = objectMapper.writeValueAsString(userData);
            Object response = rabbitTemplate.convertSendAndReceive(usersExchange.getName(),"userskey", buildMessage(usersString));
            if (response == null){
                return false;
            }
            return (Boolean) response;
        } catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return false;
    }

    private Message buildMessage(String message){
        return MessageBuilder
                .withBody(message.getBytes())
                .setContentType("application/json")
                .build();
    }
}
