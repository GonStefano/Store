package com.gon.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gon.store.entity.VideoGame;

import java.math.BigDecimal;
import java.util.List;

public interface VideoGameRepository extends JpaRepository<VideoGame, Long> {
    List<VideoGame> findByPlatform(String platform);
    List<VideoGame> findByPriceBetween(BigDecimal min, BigDecimal max);
    List<VideoGame> findByStockEquals(Integer stock);
}
