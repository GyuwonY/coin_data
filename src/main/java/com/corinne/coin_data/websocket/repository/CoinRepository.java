package com.corinne.coin_data.websocket.repository;

import com.corinne.coin_data.websocket.model.Coin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoinRepository extends JpaRepository<Coin, Long> {

    void deleteByTikerAndUser_UserId(String tiker, Long userId);
}
