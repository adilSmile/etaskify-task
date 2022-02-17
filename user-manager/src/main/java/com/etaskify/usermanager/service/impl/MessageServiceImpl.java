package com.etaskify.usermanager.service.impl;

import com.etaskify.usermanager.data.request.UserRequest;
import com.etaskify.usermanager.service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {
    private RabbitTemplate rabbitTemplate;
    private DirectExchange directExchange;
    private ObjectMapper objectMapper;
    private Queue queue;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    private static final String APPLICATION_JSON_CONTENT_TYPE = "application/json";

    @Override
    public UUID getOrganizationIdByToken(String token) {
        Message message = MessageBuilder.withBody(token.getBytes()).build();
        Object response  = rabbitTemplate.convertSendAndReceive(directExchange.getName(), routingKey, message);
        if (response == null) return null;
        return UUID.nameUUIDFromBytes((byte[]) response);
    }

    @Override
    public void sendUser(UserRequest userRequest) {
        try {
            String userString = objectMapper.writeValueAsString(userRequest);
            Message message = MessageBuilder
                    .withBody(userString.getBytes())
                    .setContentType(APPLICATION_JSON_CONTENT_TYPE)
                    .build();
            rabbitTemplate.convertAndSend(queue.getName(),message);
        } catch (JsonProcessingException e){
            e.printStackTrace();
        }
    }
}
