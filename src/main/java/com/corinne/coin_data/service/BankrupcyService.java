package com.corinne.coin_data.service;

import com.corinne.coin_data.websocket.repository.CoinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankrupcyService {
    private final CoinRepository coinRepository;

    @Autowired
    public BankrupcyService(CoinRepository coinRepository){
        this.coinRepository = coinRepository;
    }

    public void bankrupcy(Long coinId){
        coinRepository.deleteByCoinId(coinId);
    }
}
