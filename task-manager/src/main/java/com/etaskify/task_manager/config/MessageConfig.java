package com.etaskify.task_manager.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageConfig {
    @Value("${rabbitmq.auth.exchange.name}")
    private String authExchangeName;

    @Value("${rabbitmq.user.exchange.name}")
    private String userExchangeName;

    @Bean
    @Qualifier("authExchange")
    DirectExchange authExchange() {
        return new DirectExchange(authExchangeName);
    }

    @Bean
    @Qualifier("usersExchange")
    DirectExchange usersExchange() {
        return new DirectExchange(userExchangeName);
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory){
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
