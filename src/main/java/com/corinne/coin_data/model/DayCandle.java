package com.corinne.coin_data.model;

import com.corinne.coin_data.websocket.dto.DayCandleDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
@ToString
@Table(name = "tbl_daycandle")
public class DayCandle {
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Id
    private Long dayCandleId;

    @Column(nullable = false)
    private String tiker;

    @Column(nullable = false)
    private Date tradeDate;

    @Column(nullable = false)
    private int startPrice;

    @Column(nullable = false)
    private int highPrice;

    @Column(nullable = false)
    private int lowPrice;

    @Column(nullable = false)
    private int endPrice;

    @Column(nullable = false)
    private Long tradeVolume;

    public DayCandle(DayCandleDto dto){
        this.tiker = dto.getMarket();
        this.tradeDate = dto.getCandle_date_time_utc();
        this.startPrice = dto.getOpening_price();
        this.highPrice = dto.getHigh_price();
        this.lowPrice = dto.getLow_price();
        this.endPrice = dto.getTrade_price();
        this.tradeVolume = dto.getCandle_acc_trade_price();
    }
}
