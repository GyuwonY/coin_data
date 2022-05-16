package com.corinne.coin_data.repository;

import com.corinne.coin_data.model.MinuteCandle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MinuteCandleRepository extends JpaRepository<MinuteCandle, Long> {
    List<MinuteCandle> findAllByTiker(String tiker);
    MinuteCandle findFirstByTiker(String tiker);
    Long countAllByTiker(String tiker);
}
