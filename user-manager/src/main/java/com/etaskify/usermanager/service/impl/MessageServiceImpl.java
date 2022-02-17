package com.etaskify.usermanager.service.impl;

import com.etaskify.usermanager.data.request.UserRequest;
import com.etaskify.usermanager.service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MessageServiceImpl implements MessageService {

    private final RabbitTemplate rabbitTemplate;
    private final DirectExchange directExchange;
    private final ObjectMapper objectMapper;
    private final Queue queue;

    public MessageServiceImpl(final RabbitTemplate rabbitTemplate,
        final DirectExchange directExchange, final ObjectMapper objectMapper, final Queue queue) {
        this.rabbitTemplate = rabbitTemplate;
        this.directExchange = directExchange;
        this.objectMapper = objectMapper;
        this.queue = queue;
    }

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
