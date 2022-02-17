package com.etaskify.task_manager.service.impl;

import com.etaskify.task_manager.data.request.UserData;
import com.etaskify.task_manager.service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {
    private RabbitTemplate rabbitTemplate;
    private ObjectMapper objectMapper;
    @Qualifier("authExchange")
    private DirectExchange authExchange;
    @Qualifier("usersExchange")
    private DirectExchange usersExchange;

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
