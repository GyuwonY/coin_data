package com.corinne.coin_data.websocket.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PricePublishingDto implements Serializable {
    private static final long serialVersionUID = 6494678977089006639L;
    private String tiker;
    private int tradePrice;
    private int tradeDate;
    private int tradeTime;
    private Long tradeVolume;

    public PricePublishingDto(TradePrice tradePrice){
        this.tiker = tradePrice.getCode();
        this.tradePrice = tradePrice.getTrade_price();
        this.tradeDate = tradePrice.getTrade_date();
        this.tradeTime = tradePrice.getTrade_time();
        this.tradeVolume = tradePrice.getAcc_trade_price();
    }
}