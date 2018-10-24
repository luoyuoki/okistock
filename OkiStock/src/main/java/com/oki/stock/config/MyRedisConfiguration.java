package com.oki.stock.config;

import com.oki.stock.handler.SpiderExchangeMessageHandler;
import com.oki.stock.handler.SpiderNotificationMessageHandler;
import com.oki.stock.handler.SpiderStockMessageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
public class MyRedisConfiguration {

    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                            MessageListenerAdapter listenerAdapterExchange,
                                            MessageListenerAdapter listenerAdapterStock,
                                            MessageListenerAdapter listenerAdapterNotification) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapterExchange, new PatternTopic("exchange"));
        container.addMessageListener(listenerAdapterStock, new PatternTopic("stock"));
        container.addMessageListener(listenerAdapterNotification,new PatternTopic("notification"));
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapterExchange(SpiderExchangeMessageHandler receiver) {
        return new MessageListenerAdapter(receiver);
    }

    @Bean
    MessageListenerAdapter listenerAdapterStock(SpiderStockMessageHandler receiver) {
        return new MessageListenerAdapter(receiver);
    }

    @Bean
    MessageListenerAdapter listenerAdapterNotification(SpiderNotificationMessageHandler receiver){
        return new MessageListenerAdapter(receiver);
    }


}
