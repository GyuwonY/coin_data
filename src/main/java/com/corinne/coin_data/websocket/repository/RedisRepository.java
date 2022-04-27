package com.corinne.coin_data.websocket.repository;


import com.corinne.coin_data.websocket.controller.RedisSubscriber;
import com.corinne.coin_data.websocket.dto.PricePublishingDto;
import com.corinne.coin_data.websocket.model.ChatMessage;
import com.corinne.coin_data.websocket.model.MinuteCandleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
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
    private ValueOperations<String, Long> tradePrices;
    private ListOperations<String, PricePublishingDto> prices;

    @Autowired
    public RedisRepository(RedisMessageListenerContainer redisMessageListener, RedisTemplate<String, Object> redisTemplate,
                           RedisSubscriber redisSubscriber) {
        this.redisMessageListener = redisMessageListener;
        this.redisTemplate = redisTemplate;
        this.redisSubscriber = redisSubscriber;
    }

    @PostConstruct
    private void init() {
        topics = new HashMap<>();
    }

    public void savePrice(PricePublishingDto pricePublishingDto){
        prices.rightPush(pricePublishingDto.getTiker(), pricePublishingDto);
        tradePrices.set(pricePublishingDto.getTiker(), pricePublishingDto.getTradePrice());
    }


    // 스케줄러를 통해 실행
    @Transactional
    public MinuteCandleDto getMinuteCandle(String tiker){
        MinuteCandleDto minuteCandleDto = new MinuteCandleDto(tiker);
        List<PricePublishingDto> list = prices.range(tiker, 0, -1);
        minuteCandleDto.setStartPrice(list.get(0).getTradePrice());
        minuteCandleDto.setEndPrice(list.get(-1).getTradePrice());

        for(PricePublishingDto price : list){
            if(price.getTradePrice()> minuteCandleDto.getHighPrice()){
                minuteCandleDto.setHighPrice(price.getTradePrice());
            }
            if(price.getTradePrice()< minuteCandleDto.getLowPrice()){
                minuteCandleDto.setLowPrice(price.getTradePrice());
            }
        }
        redisTemplate.delete(tiker);
        return minuteCandleDto;
    }

    public Long getTradePrice(String tiker){
        return tradePrices.get(tiker);
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
