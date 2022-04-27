package com.corinne.coin_data.scheduler;

import com.corinne.coin_data.model.MinuteCandle;
import com.corinne.coin_data.repository.MinuteCandleRepository;
import com.corinne.coin_data.websocket.repository.RedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.List;

@Component
public class MinuteScheduler {
    private final MinuteCandleRepository minuteCandleRepository;
    private final RedisRepository redisRepository;
    private final List<String> tikers =  Arrays.asList("KRW-BTC","KRW-SOL","KRW-ETH","KRW-XRP", "KRW-ADA", "KRW-DOGE", "KRW-AVAX", "KRW-DOT", "KRW-MATIC", "KRW-CRO");

    @Autowired
    public MinuteScheduler(MinuteCandleRepository minuteCandleRepository, RedisRepository redisRepository){
        this.minuteCandleRepository = minuteCandleRepository;
        this.redisRepository = redisRepository;
    }

    @Scheduled(cron = "0 * * * * ?")
    public void saveMinuteCandle(){
        for(String tiker : tikers) {
            if(Long.valueOf(minuteCandleRepository.count()).intValue()> 1440);{
                minuteCandleRepository.delete(minuteCandleRepository.findFirst());
            }
            minuteCandleRepository.save(new MinuteCandle(redisRepository.getMinuteCandle(tiker)));

        }
    }
}
