package com.corinne.coin_data.websocket.controller;

import com.corinne.coin_data.websocket.dto.PricePublishingDto;
import com.corinne.coin_data.websocket.model.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RedisPublisher {
    private final RedisTemplate<String, Object> redisTemplate;

    public void publish(ChannelTopic topic, ChatMessage message) {
        redisTemplate.convertAndSend(topic.getTopic(), message);
    }

    public void publish(ChannelTopic topic, PricePublishingDto price) {
        redisTemplate.convertAndSend(topic.getTopic(), price);
    }

}
