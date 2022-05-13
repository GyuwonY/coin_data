package com.corinne.coin_data.websocket.controller;

import com.corinne.coin_data.websocket.repository.RedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TopicController {
    private final RedisRepository redisRepository;

    @Autowired
    public TopicController(RedisRepository redisRepository){
        this.redisRepository = redisRepository;
    }

    // 알람을 위한 Topic 생성 후 sub할 Topic return
    @GetMapping("/chat/{userId}")
    public void insertTopic(@PathVariable String userId){
        redisRepository.enterTopic(userId);
    }
}
