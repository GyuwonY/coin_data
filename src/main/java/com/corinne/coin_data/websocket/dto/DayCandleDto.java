package com.corinne.coin_data.websocket.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class DayCandleDto {
    private String market;
    private Date candle_date_time_utc;
    private int opening_price;
    private int high_price;
    private int low_price;
    private int trade_price;
    private Long candle_acc_trade_price;

}
