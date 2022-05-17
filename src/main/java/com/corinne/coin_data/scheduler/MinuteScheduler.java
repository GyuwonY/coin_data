package com.corinne.coin_data.scheduler;

import com.corinne.coin_data.collector.JsonUtil;
import com.corinne.coin_data.model.DayCandle;
import com.corinne.coin_data.model.MinuteCandle;
import com.corinne.coin_data.repository.DayCandleRepository;
import com.corinne.coin_data.repository.MinuteCandleRepository;
import com.corinne.coin_data.websocket.dto.DayCandleDto;
import com.corinne.coin_data.websocket.repository.RedisRepository;
import com.corinne.coin_data.websocket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.yaml.snakeyaml.util.UriEncoder;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Component
@EnableAsync
public class MinuteScheduler {
    private final MinuteCandleRepository minuteCandleRepository;
    private final RedisRepository redisRepository;
    private final List<String> tikers = Arrays.asList("KRW-BTC", "KRW-SOL", "KRW-ETH", "KRW-XRP", "KRW-ADA", "KRW-DOGE", "KRW-AVAX", "KRW-DOT", "KRW-MATIC");
    private final RestTemplate restTemplate;
    private final DayCandleRepository dayCandleRepository;

    @Autowired
    public MinuteScheduler(MinuteCandleRepository minuteCandleRepository, RedisRepository redisRepository,
                           RestTemplate restTemplate, DayCandleRepository dayCandleRepository) {
        this.minuteCandleRepository = minuteCandleRepository;
        this.redisRepository = redisRepository;
        this.restTemplate = restTemplate;
        this.dayCandleRepository = dayCandleRepository;
    }

    @Scheduled(cron = "1 * * * * ?")
    public void saveMinuteCandle() {
        for (String tiker : tikers) {
            MinuteCandle minuteCandle = new MinuteCandle(redisRepository.getMinuteCandle(tiker));
            minuteCandleRepository.save(minuteCandle);
            if (minuteCandleRepository.countAllByTiker(tiker).equals(1441L)) {
                minuteCandleRepository.delete(minuteCandleRepository.findFirstByTiker(tiker));
            }
        }
    }

    @Scheduled(cron = "2 0 0 * * ?")
    public void saveDayCandle() {
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
        for (String tiker : tikers) {
            String date = now.minusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String url = String.format("market=%s&to=%s&count=1&convertingPriceUnit=KRW",
                    tiker, date);
            String path = String.format("%s?%s", "https://api.upbit.com/v1/candles/days", UriEncoder.encode(url));
            ResponseEntity<String> response = restTemplate.exchange(URI.create(path), HttpMethod.GET, HttpEntity.EMPTY, String.class);
            List<DayCandleDto> candles = JsonUtil.listFromJson(response.getBody(), DayCandleDto.class);

            if (candles.size() != 0) {
                for (DayCandleDto candleDto : candles) {
                    DayCandle dayCandle = new DayCandle(candleDto);
                    dayCandleRepository.save(dayCandle);

                }
            }

        }
    }

    @Scheduled(cron = "*/1 * * * * *")
    public void bankruptcyCheck() {
        for(String tiker : tikers) {
            redisRepository.isBankruptcy(tiker);
        }
    }
}
