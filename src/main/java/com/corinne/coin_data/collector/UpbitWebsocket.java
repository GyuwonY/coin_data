package com.corinne.coin_data.collector;

import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocketListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class UpbitWebsocket {
    private final UpbitWebSocketListener webSocketListener;

    public void start(){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("wss://api.upbit.com/websocket/v1")
                .build();

        webSocketListener.setParameter(SiseType.TICKER, Arrays.asList("KRW-BTC","KRW-SOL","KRW-ETH","KRW-XRP", "KRW-ADA", "KRW-DOGE", "KRW-AVAX", "KRW-DOT", "KRW-MATIC", "KRW-CRO"));

        client.newWebSocket(request, webSocketListener);
        client.dispatcher().executorService().shutdown();
    }

}
