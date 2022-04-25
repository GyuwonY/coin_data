package com.corinne.coin_data.websocket.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class ChatMessage implements Serializable {

    private static final long serialVersionUID = 6494678977089006639L;

    // 메시지 타입 : 입장, 채팅
    public enum MessageType {
        ENTER, TALK
    }

    private MessageType type; // 메시지 타입
    private Long messageSender; // 메시지 보낸사람
    private String topicName;
    private String message; // 메시지
}
