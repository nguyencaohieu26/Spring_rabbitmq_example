package com.example.payment.queue;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageConfig {
    public static final String ORDER_QUEUE = "ORDER_QUEUE";
    public static final String PAYMENT_QUEUE = "PAYMENT_QUEUE";
    public static final String ORDER_EXCHANGE = "ORDER_EXCHANGE";
    public static final String ORDER_ROUTING_KEY = "ORDER_ROUTING_KEY";
    public static final String PAYMENT_ROUTING_KEY = "PAYMENT_ROUTING_KEY";

    @Bean
    public Queue queueOrder(){
        return new Queue(ORDER_QUEUE,true);
    }

    @Bean
    public Queue queuePayment(){
        return new Queue(PAYMENT_QUEUE,true);
    }

    @Bean
    public TopicExchange orderExchange(){
        return new TopicExchange(ORDER_EXCHANGE);
    }

    @Bean
    public MessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }
    @Bean
    public Binding bindingOrder(){
        return BindingBuilder.bind(queueOrder()).to(orderExchange()).with(ORDER_ROUTING_KEY);
    }
    @Bean
    public Binding bindingPayment(){
        return BindingBuilder.bind(queuePayment()).to(orderExchange()).with(PAYMENT_ROUTING_KEY);
    }
    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory){
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(converter());
        return template;
    }
}
