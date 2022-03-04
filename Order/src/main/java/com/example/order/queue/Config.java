package com.example.order.queue;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.springframework.amqp.core.BindingBuilder.bind;

@Configuration
public class Config {
    public static final String QUEUE_ORDER      = "direct.queue.order";
    public static final String QUEUE_PAYMENT    = "direct.queue.payment";
    public static final String QUEUE_INVENTORY  = "direct.queue.inventory";

    public static final String DIRECT_EXCHANGE              = "direct.exchange";
    public static final String DIRECT_ROUTING_KEY_ORDER     = "direct.routingKeyOrder";
    public static final String DIRECT_ROUTING_KEY_PAYMENT   = "direct.routingKeyPayment";
    public static final String DIRECT_ROUTING_KEY_INVENTORY = "direct.routingKeyInventory";

    @Bean
    public Declarables directBinding(){
        Queue queueOrder              = new Queue(QUEUE_ORDER);
        Queue queuePayment            = new Queue(QUEUE_PAYMENT);
        Queue queueInventory          = new Queue(QUEUE_INVENTORY);
        DirectExchange directExchange = new DirectExchange(DIRECT_EXCHANGE);
        return new Declarables(
                queueOrder,
                queuePayment,
                queueInventory,
                directExchange,
                bind(queueOrder).to(directExchange).with(DIRECT_ROUTING_KEY_ORDER),
                bind(queueInventory).to(directExchange).with(DIRECT_ROUTING_KEY_INVENTORY),
                bind(queuePayment).to(directExchange).with(DIRECT_ROUTING_KEY_PAYMENT)
        );
    }

    @Bean
    public MessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory){
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}
