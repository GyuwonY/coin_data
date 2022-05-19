package com.corinne.coin_data.service;

import com.corinne.coin_data.websocket.model.Alarm;
import com.corinne.coin_data.websocket.repository.AlarmRepository;
import com.corinne.coin_data.websocket.repository.CoinRepository;
import com.corinne.coin_data.websocket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankrupcyService {
    private final CoinRepository coinRepository;
    private final AlarmRepository alarmRepository;
    private final UserRepository userRepository;

    @Autowired
    public BankrupcyService(CoinRepository coinRepository, AlarmRepository alarmRepository, UserRepository userRepository){
        this.coinRepository = coinRepository;
        this.alarmRepository = alarmRepository;
        this.userRepository = userRepository;
    }

    public void bankrupcy(Long coinId, Long userId){
        alarmRepository.save(new Alarm(userRepository.findByUserId(userId).orElseThrow(IllegalAccessError::new),
                Alarm.AlarmType.BANKRUPTCY, coinRepository.deleteByCoinId(coinId)));
    }
}
