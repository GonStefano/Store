package com.gon.store.repository;

import org.springframework.data.repository.ListCrudRepository;

import com.gon.store.entity.VideoGame;

import java.util.List;


public interface VideoGameRepository extends ListCrudRepository<VideoGame, Long> {
    List<VideoGame> findByPlatform(String platform);
}
