package com.corinne.coin_data;

import com.corinne.coin_data.collector.UpbitWebsocket;
import com.corinne.coin_data.websocket.repository.RedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CoinDataApplication {
    @Autowired
    private UpbitWebsocket upbitWebsocket;

    @Autowired
    private RedisRepository redisRepository;

    public static final String APPLICATION_LOCATIONS = "spring.config.location="
            + "classpath:/application.yml"
            +",classpath:/application.properties";


    public static void main(String[] args) {
        new SpringApplicationBuilder(CoinDataApplication.class)
                .properties(APPLICATION_LOCATIONS)
                .run(args);
    }

    @Bean
    public ApplicationRunner applicationRunner() {
        return args -> {
            upbitWebsocket.start();
            redisRepository.enterTopic("corinnechat");
        };
    }

}
