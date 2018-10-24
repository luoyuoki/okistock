package com.oki.stock.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {

    public static final String STOCK_ORDER_QUEUE = "order.queue";

    @Bean
    public Queue queue() {
        return new Queue(STOCK_ORDER_QUEUE);
    }
}
