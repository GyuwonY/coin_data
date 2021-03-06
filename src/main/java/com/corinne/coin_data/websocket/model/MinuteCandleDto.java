package com.corinne.coin_data.websocket.model;

import com.corinne.coin_data.websocket.dto.PricePublishingDto;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MinuteCandleDto implements Serializable {
    private static final long serialVersionUID = 6494678977089006639L;
    private String tiker;
    private int highPrice;
    private int lowPrice;
    private int startPrice;
    private int endPrice;
    private int tradeDate;
    private int tradeTime;

    public MinuteCandleDto(PricePublishingDto dto){
        this.tiker = dto.getTiker();
        this.highPrice = dto.getTradePrice();
        this.startPrice = dto.getTradePrice();
        this.lowPrice = dto.getTradePrice();
        this.endPrice = dto.getTradePrice();
        this.tradeDate = dto.getTradeDate();
        this.tradeTime = dto.getTradeTime()/100;
    }
}
