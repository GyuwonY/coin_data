package com.corinne.coin_data.scheduler;

import com.corinne.coin_data.model.MinuteCandle;
import com.corinne.coin_data.repository.MinuteCandleRepository;
import com.corinne.coin_data.websocket.repository.RedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.List;

@Component
@EnableAsync
public class MinuteScheduler {
    private final MinuteCandleRepository minuteCandleRepository;
    private final RedisRepository redisRepository;
    private final List<String> tikers = Arrays.asList("KRW-BTC","KRW-SOL","KRW-ETH","KRW-XRP", "KRW-ADA", "KRW-DOGE", "KRW-AVAX", "KRW-DOT", "KRW-MATIC");

    @Autowired
    public MinuteScheduler(MinuteCandleRepository minuteCandleRepository, RedisRepository redisRepository){
        this.minuteCandleRepository = minuteCandleRepository;
        this.redisRepository = redisRepository;
    }

    @Scheduled(cron = "1 * * * * ?")
    public void saveMinuteCandle(){
        for(String tiker : tikers) {
            MinuteCandle minuteCandle = new MinuteCandle(redisRepository.getMinuteCandle(tiker));
            minuteCandleRepository.save(minuteCandle);
            if(minuteCandleRepository.countAllByTiker(tiker).equals(1441L)){
                minuteCandleRepository.delete(minuteCandleRepository.findFirstByTiker(tiker));
            }

        }
    }
}
