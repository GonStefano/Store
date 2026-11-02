package com.gon.store.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.gon.store.dto.PriceBetweenRequestDTO;
import com.gon.store.dto.VideoGameRequestDTO;
import com.gon.store.dto.VideoGameResponseDTO;
import com.gon.store.service.VideoGameService;

import jakarta.validation.Valid;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/videogames")
public class VideoGameController {
    private final VideoGameService videoGameService;

    private VideoGameController(VideoGameService videoGameService){
        this.videoGameService = videoGameService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<VideoGameResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(videoGameService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<VideoGameResponseDTO>> findAll() {
        return ResponseEntity.ok(videoGameService.findAll());
    }

    @PostMapping
    public ResponseEntity<Void> createVideoGame(@Valid @RequestBody VideoGameRequestDTO videoGame, UriComponentsBuilder ucb) {
        VideoGameResponseDTO game = videoGameService.createVideoGame(videoGame);
        URI locationOfNewVideoGame = ucb
            .path("/api/videogames/{id}")
            .buildAndExpand(game.id())
            .toUri();
        return ResponseEntity.created(locationOfNewVideoGame).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateVideoGame(@PathVariable Long id, @Valid @RequestBody VideoGameRequestDTO videoGame) {
        videoGameService.updateVideoGame(id, videoGame);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVideoGame(@PathVariable Long id){
        videoGameService.deleteVideoGame(id);
        return  ResponseEntity.noContent().build();
    }

    @GetMapping("/platform/{platform}")
    public ResponseEntity<List<VideoGameResponseDTO>> getPlatform(@PathVariable String platform) {
        return ResponseEntity.ok(videoGameService.findByPlatform(platform));
    }

    @GetMapping("/price")
    public ResponseEntity<List<VideoGameResponseDTO>> getGameByPriceBetween (@Valid @ModelAttribute PriceBetweenRequestDTO prices) {
        return ResponseEntity.ok(videoGameService.findByPriceBetween(prices));
    }

    @GetMapping("/out-of-stock")
    public ResponseEntity<List<VideoGameResponseDTO>> getGameByStockEquals() {
        return ResponseEntity.ok(videoGameService.findByStockEquals());
    }

}
