package com.gon.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gon.store.entity.VideoGame;

import java.math.BigDecimal;
import java.util.List;

public interface VideoGameRepository extends JpaRepository<VideoGame, Long> {
    List<VideoGame> findByPlatform(String platform);
    List<VideoGame> findByPriceBetween(BigDecimal min, BigDecimal max);
    List<VideoGame> findByStockEquals(Integer stock);

    @Modifying
    @Query("UPDATE VideoGame v SET v.stock = v.stock - 1 WHERE v.id = :id")
    void decrementStock(@Param("id") Long id);
}
