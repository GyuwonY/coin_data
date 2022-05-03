package com.corinne.coin_data.repository;


import com.corinne.coin_data.model.DayCandle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DayCandleRepository extends JpaRepository<DayCandle, Long> {
}
