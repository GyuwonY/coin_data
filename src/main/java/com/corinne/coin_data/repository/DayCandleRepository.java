package com.corinne.coin_data.repository;


import com.corinne.coin_data.model.DayCandle;
import com.corinne.coin_data.model.MinuteCandle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface DayCandleRepository extends JpaRepository<DayCandle, Long> {
    Page<DayCandle> findAllByTiker(String tiker, Pageable pageable);
    MinuteCandle findFirstByTiker(String tiker);
    Long countAllByTiker(String tiker);
}
