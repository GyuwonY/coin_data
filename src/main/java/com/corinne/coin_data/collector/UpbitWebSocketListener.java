package com.corinne.coin_data.collector;

import com.corinne.coin_data.websocket.controller.RedisPublisher;
import com.corinne.coin_data.websocket.dto.PricePublishingDto;
import com.corinne.coin_data.websocket.dto.TradePrice;
import com.corinne.coin_data.websocket.repository.RedisRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Configuration
public class UpbitWebSocketListener extends WebSocketListener {
    private static final int NORMAL_CLOSURE_STATUS = 1000;
    private String json;
    private TradePrice tradePrice;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RedisPublisher redisPublisher;
    @Autowired
    private RedisRepository redisRepository;

    @Override
    public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        System.out.printf("Socket Closed : %s / %s\r\n", code, reason);
    }

    @Override
    public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        System.out.printf("Socket Closing : %s / %s\n", code, reason);
        webSocket.close(NORMAL_CLOSURE_STATUS, null);
        webSocket.cancel();
    }

    @Override
    public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
        System.out.println("Socket Error : " + t.getMessage());
        t.printStackTrace();
    }

    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
        JsonNode jsonNode = JsonUtil.fromJson(text, JsonNode.class);
        System.out.println(jsonNode.toPrettyString());
    }


    //실질적인 데이터가 찍히는 곳
    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull ByteString bytes) {
        try {
            tradePrice = objectMapper.readValue(bytes.string(StandardCharsets.UTF_8), TradePrice.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if(tradePrice.getStream_type().equals("SNAPSHOT")){
            redisRepository.enterTopic(tradePrice.getCode());
        }else {
            redisPublisher.publish(redisRepository.getTopic(tradePrice.getCode()), new PricePublishingDto(tradePrice));
            redisRepository.savePrice(new PricePublishingDto(tradePrice));
        }
    }

    @Override
    public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
        webSocket.send(getParameter());
//        webSocket.close(NORMAL_CLOSURE_STATUS, null); // 없을 경우 끊임없이 서버와 통신함
    }

    public void setParameter(SiseType siseType, List<String> codes) {
        this.json = JsonUtil.toJson(Arrays.asList(Ticket.of(UUID.randomUUID().toString()), Type.of(siseType, codes)));
    }

    private String getParameter() {
        return this.json;
    }

    @Data(staticConstructor = "of")
    private static class Ticket {
        private final String ticket;
    }

    @Data(staticConstructor = "of")
    private static class Type {
        private final SiseType type;
        private final List<String> codes; // market
    }

}
