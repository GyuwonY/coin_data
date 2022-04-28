package com.corinne.coin_data.scheduler;

import com.corinne.coin_data.model.MinuteCandle;
import com.corinne.coin_data.repository.MinuteCandleRepository;
import com.corinne.coin_data.websocket.repository.RedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@Component
@EnableAsync
public class MinuteScheduler {
    private final MinuteCandleRepository minuteCandleRepository;
    private final RedisRepository redisRepository;
    private final List<String> tikers = Arrays.asList("KRW-BTC","KRW-SOL","KRW-ETH","KRW-XRP", "KRW-ADA", "KRW-DOGE", "KRW-AVAX", "KRW-DOT", "KRW-MATIC", "KRW-CRO");

    @Autowired
    public MinuteScheduler(MinuteCandleRepository minuteCandleRepository, RedisRepository redisRepository){
        this.minuteCandleRepository = minuteCandleRepository;
        this.redisRepository = redisRepository;
    }

    @Scheduled(cron = "0 * * * * ?")
    public void saveMinuteCandle() throws IllegalAccessException {
        for(String tiker : tikers) {

            MinuteCandle minuteCandle = new MinuteCandle(redisRepository.getMinuteCandle(tiker));
            if(isNull(minuteCandle)){
                minuteCandle = new MinuteCandle(redisRepository.getLastCandle(tiker));
            }
            minuteCandleRepository.save(minuteCandle);
            if(minuteCandleRepository.countAllByTiker(tiker).equals(1440L)){
                minuteCandleRepository.delete(minuteCandleRepository.findFirstByTiker(tiker));
            }

        }
    }

    private boolean isNull(MinuteCandle minuteCandle) throws IllegalAccessException {
        for (Field f : minuteCandle.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            if (!f.getName().equals("minuteCandleId")) {
                if (f.get(minuteCandle) == null) {
                    return true;
                }
            }
        }
        return false;
    }
}
