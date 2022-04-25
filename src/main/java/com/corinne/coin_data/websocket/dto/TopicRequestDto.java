package com.corinne.coin_data.websocket.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TopicRequestDto {
    private String chartTopic;
    private String chatTopic;

    public TopicRequestDto(String chartTopic, String chatTopic){
        this.chartTopic = chartTopic;
        this.chatTopic = chatTopic;
    }
}
