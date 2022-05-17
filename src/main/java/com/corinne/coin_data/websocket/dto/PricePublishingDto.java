package com.corinne.coin_data.websocket.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PricePublishingDto implements Serializable {

    private static final long serialVersionUID = 6494678977089006639L;
    private String tiker;
    private int tradePrice;
    private int tradeDate;
    private int tradeTime;
    private Long tradeVolume;
    private int highPrice;
    private int lowPrice;
    private int prevClosingPrice;
    private int signedChangePrice;
    private float signedChangeRate;

    public PricePublishingDto(TradePrice tradePrice){
        this.tiker = tradePrice.getCode();
        this.tradePrice = tradePrice.getTrade_price();
        this.tradeDate = tradePrice.getTrade_date();
        this.tradeTime = tradePrice.getTrade_time();
        this.tradeVolume = tradePrice.getAcc_trade_price();
        this.highPrice = tradePrice.getHigh_price();
        this.lowPrice = tradePrice.getLow_price();
        this.prevClosingPrice = tradePrice.getPrev_closing_price();
        this.signedChangePrice = tradePrice.getSigned_change_price();
        this.signedChangeRate = tradePrice.getSigned_change_rate()*100;
    }
    public PricePublishingDto(String tiker){
        this.tiker=tiker;
    }
}
