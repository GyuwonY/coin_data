package com.corinne.coin_data.websocket.model;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage implements Serializable {

    private static final long serialVersionUID = 6494678977089006639L;

    // 메시지 타입 : 입장, 채팅
    public enum MessageType {
        ENTER, TALK, ALARM, BANKRUPTCY
    }

    private MessageType type; // 메시지 타입
    private String sendTime;
    private String topicName;
    private String nickname; // 메시지 보낸사람
    private String imageUrl;
    private Long userId;
    private boolean clear;
    private Long exp;
    private String message; // 메시지

    public ChatMessage(BankruptcyDto dto){
        this.type = MessageType.BANKRUPTCY;
        this.sendTime = LocalDateTime.now().plusHours(9).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.topicName = Long.toString(dto.getUserId());
        this.message = "종목 : " + dto.getTiker() + "<br/>\n청산가 : " + dto.getBankruptcyPrice() + "원이 되어 청산되었습니다.";
    }
}
