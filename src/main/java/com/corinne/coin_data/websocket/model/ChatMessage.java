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
        ENTER, TALK, ALARM
    }

    private MessageType type; // 메시지 타입
    private String sendTime;
    private String topicName;
    private String nickname; // 메시지 보낸사람
    private String imageUrl;
    private Long exp;
    private String message; // 메시지

    public ChatMessage(BankruptcyDto dto){
        this.type = MessageType.ALARM;
        this.message = "tiker : " + dto.getTiker() + "\n청산가 : " + dto.getBankruptcyPrice() + "원이 되어 청산되었습니다.";
    }
}
