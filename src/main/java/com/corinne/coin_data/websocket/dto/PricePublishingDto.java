package com.corinne.coin_data.websocket.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PricePublishingDto {
    private String tiker;
    private int tradePrice;
    private int tradeDate;
    private int tradeTime;
    private Long tradeVolume;

    public PricePublishingDto(TradePrice tradePrice){
        this.tiker = tradePrice.getCode();
        this.tradePrice = tradePrice.getTrade_price();
        this.tradeDate = tradePrice.getTrade_date();
        this.tradeTime = tradePrice.getTrade_date();
        this.tradeVolume = tradePrice.getAcc_trade_volume();
    }
}
