package com.corinne.coin_data.repository;

import com.corinne.coin_data.model.MinuteCandle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MinuteCandleRepository extends JpaRepository<MinuteCandle, Long> {
    MinuteCandle findFirst();
}
