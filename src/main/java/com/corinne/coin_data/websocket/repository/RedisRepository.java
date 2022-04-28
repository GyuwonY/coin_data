package com.corinne.coin_data.websocket.repository;

import com.corinne.coin_data.websocket.controller.RedisSubscriber;
import com.corinne.coin_data.websocket.dto.PricePublishingDto;
import com.corinne.coin_data.websocket.model.ChatMessage;
import com.corinne.coin_data.websocket.model.MinuteCandleDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Repository;
import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Repository
public class RedisRepository {
    // 채팅방(topic)에 발행되는 메시지를 처리할 Listner
    private final RedisMessageListenerContainer redisMessageListener;
    // 구독 처리 서비스
    private final RedisSubscriber redisSubscriber;
    // Redis
    private final RedisTemplate<String, Object> redisTemplate;
    // 채팅방의 대화 메시지를 발행하기 위한 redis topic 정보. 서버별로 채팅방에 매치되는 topic정보를 Map에 넣어 roomId로 찾을수 있도록 한다.
    private Map<String, ChannelTopic> topics;
    private ValueOperations<String, Object> tradePrice;
    private ListOperations<String, Object> prices;
    private HashOperations<String, String, Object> lastCandle;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    public RedisRepository(RedisMessageListenerContainer redisMessageListener, RedisTemplate<String, Object> redisTemplate,
                           RedisSubscriber redisSubscriber) {
        this.redisMessageListener = redisMessageListener;
        this.redisTemplate = redisTemplate;
        this.redisSubscriber = redisSubscriber;
    }

    @PostConstruct
    private void init() {
        tradePrice = redisTemplate.opsForValue();
        prices = redisTemplate.opsForList();
        lastCandle = redisTemplate.opsForHash();
        topics = new HashMap<>();
    }

    public void savePrice(PricePublishingDto pricePublishingDto){
        prices.rightPush(pricePublishingDto.getTiker(), pricePublishingDto);
        tradePrice.set(pricePublishingDto.getTiker()+"tradeprice", pricePublishingDto.getTradePrice());
    }


    // 스케줄러를 통해 실행
    public MinuteCandleDto getMinuteCandle(String tiker){
        PricePublishingDto firstPrice = objectMapper.convertValue(prices.leftPop(tiker), PricePublishingDto.class);
        MinuteCandleDto minuteCandleDto = new MinuteCandleDto(firstPrice);
        while (true){
            PricePublishingDto price = objectMapper.convertValue(prices.leftPop(tiker), PricePublishingDto.class);
            if(firstPrice.getTradeTime()/100==price.getTradeTime()/100) {
                if (price.getTradePrice() > minuteCandleDto.getHighPrice()) {
                    minuteCandleDto.setHighPrice(price.getTradePrice());
                }
                if (price.getTradePrice() < minuteCandleDto.getLowPrice()) {
                    minuteCandleDto.setLowPrice(price.getTradePrice());
                }
                minuteCandleDto.setEndPrice(price.getTradePrice());
            }else {
                prices.leftPush(tiker, price);
                break;
            }
        }

        lastCandle.put("lastcandle", tiker, minuteCandleDto);

        return minuteCandleDto;
    }

    public MinuteCandleDto getLastCandle(String tiker) {
        MinuteCandleDto minuteCandleDto = objectMapper.convertValue(lastCandle.get("lastcandle", tiker), MinuteCandleDto.class);
        minuteCandleDto.setTradeTime(minuteCandleDto.getTradeTime()+1L);
        System.out.println(minuteCandleDto);
        lastCandle.put("lastcandle", tiker, minuteCandleDto);
        return minuteCandleDto;
    }

    public Long getTradePrice(String tiker){
        return (Long)tradePrice.get(tiker+"tradeprice");
    }

    /**
     * 채팅방 입장 : redis에 topic을 만들고 pub/sub 통신을 하기 위해 리스너를 설정한다.
     */
    public void enterChatRoom(ChatMessage message) {
        ChannelTopic topic = topics.get(message.getTopicName());
        if (topic == null)
            topic = new ChannelTopic(message.getTopicName());
        redisMessageListener.addMessageListener(redisSubscriber, topic);
        topics.put(message.getTopicName(), topic);
    }

    public void enterTopic(String topicName) {
        ChannelTopic topic = topics.get(topicName);
        if (topic == null) {
            topic = new ChannelTopic(topicName);
        }
        redisMessageListener.addMessageListener(redisSubscriber, topic);
        topics.put(topicName, topic);
    }

    public ChannelTopic getTopic(String topicName) {
        return topics.get(topicName);
    }


}
