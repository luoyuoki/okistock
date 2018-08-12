package com.oki.stock.config;

import com.oki.stock.handler.SpiderExchangeMessageReceiver;
import com.oki.stock.handler.SpiderNotificationMessageReceiver;
import com.oki.stock.handler.SpiderStockMessageReceiver;
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
    MessageListenerAdapter listenerAdapterExchange(SpiderExchangeMessageReceiver receiver) {
        return new MessageListenerAdapter(receiver);
    }

    @Bean
    MessageListenerAdapter listenerAdapterStock(SpiderStockMessageReceiver receiver) {
        return new MessageListenerAdapter(receiver);
    }

    @Bean
    MessageListenerAdapter listenerAdapterNotification(SpiderNotificationMessageReceiver receiver){
        return new MessageListenerAdapter(receiver);
    }


}
