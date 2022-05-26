package com.corinne.coin_data.websocket.repository;

import com.corinne.coin_data.service.BankrupcyService;
import com.corinne.coin_data.websocket.dto.PricePublishingDto;
import com.corinne.coin_data.websocket.model.BankruptcyDto;
import com.corinne.coin_data.websocket.model.ChatMessage;
import com.corinne.coin_data.websocket.model.MinuteCandleDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Repository;
import javax.annotation.PostConstruct;

@Repository
public class RedisRepository {
    private final BankrupcyService bankrupcyService;
    // Redis
    private final RedisTemplate<String, Object> redisTemplate;
    private ValueOperations<String, Object> tradePrice;
    private ListOperations<String, Object> prices;
    private HashOperations<String, String, Object> lastCandles;
    private final ChannelTopic channelTopic;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    public RedisRepository(RedisTemplate<String, Object> redisTemplate, ChannelTopic channelTopic,
                          BankrupcyService bankrupcyService) {
        this.redisTemplate = redisTemplate;
        this.bankrupcyService = bankrupcyService;
        this.channelTopic = channelTopic;
    }

    @PostConstruct
    private void init() {
        tradePrice = redisTemplate.opsForValue();
        prices = redisTemplate.opsForList();
        lastCandles = redisTemplate.opsForHash();
    }

    /**
     * 실시간 현재가 저장 메소드
     */
    public void savePrice(PricePublishingDto pricePublishingDto){
        prices.rightPush(pricePublishingDto.getTiker(), pricePublishingDto);
        tradePrice.set(pricePublishingDto.getTiker()+"tradeprice", pricePublishingDto);
    }

    /**
     * 파산인 경우 보유 코인 삭제처리
     */
    public void isBankruptcy(String tiker){
        BankruptcyDto checkDto = objectMapper.convertValue(prices.leftPop(tiker + "bankruptcy"), BankruptcyDto.class);
        PricePublishingDto price = objectMapper.convertValue(tradePrice.get(tiker+"tradeprice"), PricePublishingDto.class);
        if(checkDto != null){

            prices.leftPush(tiker + "bankruptcy", checkDto);

            for (Long i = 0L; i <= prices.size(tiker + "bankruptcy"); i++) {
                BankruptcyDto bankruptcyDto = objectMapper.convertValue(
                        prices.index(tiker + "bankruptcy", i), BankruptcyDto.class);

                if (price.getTradePrice() <= bankruptcyDto.getBankruptcyPrice()) {
                    bankrupcyService.bankrupcy(bankruptcyDto.getCoinId(), bankruptcyDto.getUserId());
                    prices.remove(tiker + "bankruptcy", i, bankruptcyDto);
                    ChatMessage alarm = new ChatMessage(bankruptcyDto);
                    redisTemplate.convertAndSend(channelTopic.getTopic(), alarm);
                }
            }
        }
    }

    /**
     * 스케줄러를 통해 실행 - 캐시 DB에 저장된 실시간 데이터를 이용하여 분봉 dto를 생성하여 리턴한다.
     */
    public MinuteCandleDto getMinuteCandle(String tiker){
        PricePublishingDto firstPrice = objectMapper.convertValue(prices.leftPop(tiker), PricePublishingDto.class);
        MinuteCandleDto minuteCandleDto;

        if(firstPrice == null) {
            minuteCandleDto = getLastCandle(tiker);
        }else {
            minuteCandleDto = new MinuteCandleDto(firstPrice);

            while (true) {
                PricePublishingDto price = objectMapper.convertValue(prices.leftPop(tiker), PricePublishingDto.class);

                if (price == null) {
                    break;
                }

                if (firstPrice.getTradeTime() / 100 == price.getTradeTime() / 100) {
                    if (price.getTradePrice() > minuteCandleDto.getHighPrice()) {
                        minuteCandleDto.setHighPrice(price.getTradePrice());
                    }

                    if (price.getTradePrice() < minuteCandleDto.getLowPrice()) {
                        minuteCandleDto.setLowPrice(price.getTradePrice());
                    }

                    minuteCandleDto.setEndPrice(price.getTradePrice());
                } else {
                    prices.leftPush(tiker, price);
                    break;
                }
            }
        }

        lastCandles.put("lastcandle", tiker, minuteCandleDto);

        return minuteCandleDto;
    }

    /**
     * 1분동안 거래가 발생되지 않는 경우 마지막으로 거래된 분봉 정보를 통해 분봉을 만든다.
     */
    private MinuteCandleDto getLastCandle(String tiker) {
        MinuteCandleDto minuteCandleDto = objectMapper.convertValue(lastCandles.get("lastcandle", tiker), MinuteCandleDto.class);

        if(minuteCandleDto == null){
            minuteCandleDto = new MinuteCandleDto(tiker, 0, 0, 0, 0, 0, 0);
        }else {
            minuteCandleDto.setTradeTime(minuteCandleDto.getTradeTime() + 1);
            minuteCandleDto.setHighPrice(minuteCandleDto.getEndPrice());
            minuteCandleDto.setLowPrice(minuteCandleDto.getEndPrice());
            minuteCandleDto.setStartPrice(minuteCandleDto.getEndPrice());
            lastCandles.put("lastcandle", tiker, minuteCandleDto);
        }

        return minuteCandleDto;
    }


}
