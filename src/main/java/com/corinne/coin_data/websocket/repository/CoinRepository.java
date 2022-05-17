package com.corinne.coin_data.websocket.repository;

import com.corinne.coin_data.websocket.model.Coin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface CoinRepository extends JpaRepository<Coin, Long> {

    void deleteByTikerAndUser_UserId(String tiker, Long userId);
    @Transactional
    void deleteByCoinId(Long coinId);
}
