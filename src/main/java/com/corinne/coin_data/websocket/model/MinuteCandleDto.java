package com.corinne.coin_data.websocket.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MinuteCandleDto {
    private String tiker;
    private Long highPrice;
    private Long lowPrice;
    private Long startPrice;
    private Long endPrice;
    private Long tradeDate;
    private Long tradeTime;

    public MinuteCandleDto(String tiker){
        this.tiker = tiker;
    }
}
