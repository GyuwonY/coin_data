package com.corinne.coin_data.websocket.handler;


import com.corinne.coin_data.websocket.jwt.HeaderTokenExtractor;
import com.corinne.coin_data.websocket.jwt.JwtDecoder;
import com.corinne.coin_data.websocket.model.User;
import com.corinne.coin_data.websocket.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor {
    private final HeaderTokenExtractor headerTokenExtractor;
    private final JwtDecoder jwtDecoder;

    // websocket을 통해 들어온 요청이 처리 되기전 실행된다.
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        if (StompCommand.CONNECT == accessor.getCommand()) {
            jwtDecoder.decodeId(headerTokenExtractor.extract(accessor.getFirstNativeHeader("token")));
        }else if (StompCommand.DISCONNECT == accessor.getCommand()) {
        }
        return message;
    }
}