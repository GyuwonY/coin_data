package com.corinne.coin_data.websocket.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class TradePrice {
    public String type; // ticker
    public String code; // market KRW-BTC
    public Long opening_price; // 시가
    public Long high_price; // 고가
    public Long low_price;// 저가
    public Long trade_price; // 현재가
    public Long prev_closing_price; // 전일 종가
    public String change; // RISE : 상승, EVEN : 보합, FALL : 하락  TODO enum
    public Long change_price; // 부호 없는 전일 대비 값
    public Long signed_change_price; // 전일 대비 값
    public Long change_rate; // 부호 없는 전일 대비 등락율
    public Long signed_change_rate; // 전일 대비 등락율
    public String ask_bid; // 매수/매도 구분
    public Long trade_volume; // 가장 최근 거래량
    public Long acc_trade_volume; // 누적 거래량(UTC 0시 기준)
    public Long acc_trade_volume_24h; // 24시간 누적 거래량
    public Long acc_trade_price; // 누적 거래대금(UTC 0시 기준)
    public Long acc_trade_price_24h; // 24시간 누적 거래대금
    public Long trade_date; // 최근 거래 일자(UTC) yyyyMMdd
    public Long trade_time; // 최근 거래 시각(UTC) HHmmss
    public Long trade_timestamp; // 체결 타임스탬프 (milliseconds)
    public Long acc_ask_volume; // 누적 매도량
    public Long acc_bid_volume; // 누적 매수량
    public Long highest_52_week_price; // 52주 최고가
    public String highest_52_week_date; // 52주 최고가 달성일
    public Long lowest_52_week_price; // 52주 최저가
    public String lowest_52_week_date; // 52주 최저가 달성일
    public String market_state; // 거래상태 TODO enum PREVIEW : 입금지원, ACTIVE : 거래지원가능, DELISTED : 거래지원종료
    public Boolean is_trading_suspended; // 거래 정지 여부
    public LocalDateTime delisting_date; // 상장폐지일
    public String market_warning; // 유의 종목 여부	 TODO enum NONE : 해당없음, CAUTION : 투자유의
    public Long timestamp; // 타임스탬프 (milliseconds)
    public String stream_type; // TODO enum SNAPSHOT : 스냅샷, REALTIME : 실시간
}
