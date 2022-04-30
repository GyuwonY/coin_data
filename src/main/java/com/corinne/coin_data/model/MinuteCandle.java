package com.corinne.coin_data.model;

import com.corinne.coin_data.websocket.model.MinuteCandleDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@ToString
@Table(name = "tbl_minutecandle")
public class MinuteCandle {
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Id
    private Long minuteCandleId;

    @Column(nullable = false)
    private String tiker;

    @Column(nullable = false)
    private int highPrice;

    @Column(nullable = false)
    private int lowPrice;

    @Column(nullable = false)
    private int startPrice;

    @Column(nullable = false)
    private int endPrice;

    @Column(nullable = false)
    private int tradeDate;

    @Column(nullable = false)
    private int tradeTime;

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
