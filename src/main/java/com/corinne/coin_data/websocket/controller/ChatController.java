package com.corinne.coin_data.websocket.controller;

import com.corinne.coin_data.websocket.jwt.HeaderTokenExtractor;
import com.corinne.coin_data.websocket.jwt.JwtDecoder;
import com.corinne.coin_data.websocket.model.ChatMessage;
import com.corinne.coin_data.websocket.repository.RedisRepository;
import com.corinne.coin_data.websocket.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ChatController {
    private final RedisPublisher redisPublisher;
    private final RedisRepository redisRepository;

    /**
     * websocket "/pub/chat/message"로 들어오는 메시징을 처리한다.
     */
    @MessageMapping("/chat/message")
    public void message(ChatMessage message) {
        if(ChatMessage.MessageType.ENTER.equals(message.getType())){
            message.setMessage(message.getMessage() + "님이 입장하셨습니다.");
        }

        // Websocket에 발행된 메시지를 redis로 발행한다(publish)
        redisPublisher.publish(redisRepository.getTopic("corinnechat"), message);
    }
}
