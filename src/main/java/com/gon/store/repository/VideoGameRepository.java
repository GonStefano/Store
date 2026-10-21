package com.gon.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gon.store.entity.VideoGame;

import java.util.List;

public interface VideoGameRepository extends JpaRepository<VideoGame, Long> {
    List<VideoGame> findByPlatform(String platform);
}
