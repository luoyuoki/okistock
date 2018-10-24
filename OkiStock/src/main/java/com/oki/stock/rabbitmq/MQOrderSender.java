package com.oki.stock.rabbitmq;

import com.oki.stock.entity.Order;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQOrderSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send(Order order) {
        amqpTemplate.convertAndSend(MQConfig.STOCK_ORDER_QUEUE, order);
    }
}
