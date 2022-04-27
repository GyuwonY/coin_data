package com.corinne.coin_data.websocket.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class PricePublishingDto implements Serializable {
    private static final long serialVersionUID = 6494678977089006639L;
    private String tiker;
    private Long tradePrice;
    private Long tradeDate;
    private Long tradeTime;
    private Long tradeVolume;

    public PricePublishingDto(TradePrice tradePrice){
        this.tiker = tradePrice.getCode();
        this.tradePrice = tradePrice.getTrade_price();
        this.tradeDate = tradePrice.getTrade_date();
        this.tradeTime = tradePrice.getTrade_date();
        this.tradeVolume = tradePrice.getAcc_trade_volume();
    }
}
