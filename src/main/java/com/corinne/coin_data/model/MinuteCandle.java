package com.corinne.coin_data.model;

import com.corinne.coin_data.websocket.model.MinuteCandleDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "tbl_minutecandle")
public class MinuteCandle {
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Id
    private Long minuteCandleId;

    @Column(nullable = false)
    private String tiker;

    @Column(nullable = false)
    private Long highPrice;

    @Column(nullable = false)
    private Long lowPrice;

    @Column(nullable = false)
    private Long startPrice;

    @Column(nullable = false)
    private Long endPrice;

    @Column(nullable = false)
    private Long tradeDate;

    @Column(nullable = false)
    private Long tradeTime;

    public MinuteCandle(MinuteCandleDto minuteCandleDto){
        this.tiker = minuteCandleDto.getTiker();
        this.highPrice = minuteCandleDto.getHighPrice();
        this.lowPrice = minuteCandleDto.getLowPrice();
        this.startPrice = minuteCandleDto.getStartPrice();
        this.endPrice = minuteCandleDto.getEndPrice();
        this.tradeDate = minuteCandleDto.getTradeDate();
        this.tradeTime = minuteCandleDto.getTradeTime();
    }

}
