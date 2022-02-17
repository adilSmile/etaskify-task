package com.etaskify.authorization.config;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;


@Configuration
public class MessageConfig implements RabbitListenerConfigurer {

  @Value("${rabbitmq.queue.name}")
  private String queueName;

  @Value("${rabbitmq.user.queue.name}")
  private String userQueueName;

  @Value("${rabbitmq.user.exchange.name}")
  private String userExchangeName;

  @Value("${rabbitmq.user.routing.key}")
  private String userRoutingKey;

  @Value("${rabbitmq.auth.queue.name}")
  private String authQueueName;

  @Value("${rabbitmq.auth.exchange.name}")
  private String authExchangeName;

  @Value("${rabbitmq.auth.routing.key}")
  private String authRoutingKey;

  @Bean
  @Qualifier("uQueue") Queue queue() {
    return new Queue(queueName, false);
  }

  @Bean
  @Qualifier("userQueue")
  Queue userQueue(){
    return new Queue(userQueueName, false);
  }

  @Bean
  @Qualifier("userExchange") DirectExchange userExchange(){
    return new DirectExchange(userExchangeName);
  }

  @Bean
  @Qualifier("userBinding") Binding userBinding(@Qualifier("userQueue") Queue queue, @Qualifier("userExchange") DirectExchange exchange){
    return BindingBuilder.bind(queue).to(exchange).with(userRoutingKey);
  }

  @Bean
  @Qualifier("authQueue")
  Queue authQueue(){
    return new Queue(authQueueName, false);
  }

  @Bean
  @Qualifier("authExchange")
  DirectExchange authExchange(){
    return new DirectExchange(authExchangeName);
  }

  @Bean
  @Qualifier("userBinding")
  Binding authBinding(@Qualifier("authQueue") Queue queue, @Qualifier("authExchange") DirectExchange exchange){
    return BindingBuilder.bind(queue).to(exchange).with(authRoutingKey);
  }

  @Bean
  public MappingJackson2MessageConverter converter() {
    return new MappingJackson2MessageConverter();
  }

  @Bean Jackson2JsonMessageConverter jsonMessageConverter() {
    return new Jackson2JsonMessageConverter();
  }

  @Bean MessageHandlerMethodFactory messageHandlerMethodFactory() {
    DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
    factory.setMessageConverter(converter());
    return factory;
  }

  @Override
  public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
    registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
  }

  @Bean
  public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
    return new RabbitAdmin(connectionFactory);
  }
}
