package com.corinne.coin_data;

import com.corinne.coin_data.collector.UpbitWebsocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CoinDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoinDataApplication.class, args);
    }

    @Autowired
    private UpbitWebsocket upbitWebsocket;

    @Bean
    public ApplicationRunner applicationRunner() {
        return args -> upbitWebsocket.start();
    }

}
