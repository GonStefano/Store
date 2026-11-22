package com.gon.store.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.gon.store.dto.PriceBetweenRequestDTO;
import com.gon.store.dto.VideoGameRequestDTO;
import com.gon.store.dto.VideoGameResponseDTO;
import com.gon.store.service.VideoGameService;

import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Get videogame by id.", description = "Retrieves video game details by its unique identifier.")
    @GetMapping("/{id}")
    public ResponseEntity<VideoGameResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(videoGameService.findById(id));
    }

    @Operation(summary = "Get all videogames.", description = "Retrieves a complete list of all video games in the catalog.")
    @GetMapping
    public ResponseEntity<List<VideoGameResponseDTO>> findAll() {
        return ResponseEntity.ok(videoGameService.findAll());
    }

    @Operation(summary = "Create a new VideoGame.", description = "Adds and registers a new video game into the store catalog.")
    @PostMapping
    public ResponseEntity<Void> createVideoGame(@Valid @RequestBody VideoGameRequestDTO videoGame, UriComponentsBuilder ucb) {
        VideoGameResponseDTO game = videoGameService.createVideoGame(videoGame);
        URI locationOfNewVideoGame = ucb
            .path("/api/videogames/{id}")
            .buildAndExpand(game.id())
            .toUri();
        return ResponseEntity.created(locationOfNewVideoGame).build();
    }

    @Operation(summary = "Update a created game.", description = "Updates details of an existing video game by its ID.")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateVideoGame(@PathVariable Long id, @Valid @RequestBody VideoGameRequestDTO videoGame) {
        videoGameService.updateVideoGame(id, videoGame);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete a created game by id.", description = "Deletes a video game from the catalog by its ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVideoGame(@PathVariable Long id){
        videoGameService.deleteVideoGame(id);
        return  ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get videogames by platform.", description = "Retrieves all video games available for the specified platform.")
    @GetMapping("/platform/{platform}")
    public ResponseEntity<List<VideoGameResponseDTO>> getPlatform(@PathVariable String platform) {
        return ResponseEntity.ok(videoGameService.findByPlatform(platform));
    }

    @Operation(summary = "Get videogames within price range.", description = "Filters and retrieves video games within a specified minimum and maximum price range.")
    @GetMapping("/price")
    public ResponseEntity<List<VideoGameResponseDTO>> getGameByPriceBetween (@Valid @ModelAttribute PriceBetweenRequestDTO prices) {
        return ResponseEntity.ok(videoGameService.findByPriceBetween(prices));
    }

    @Operation(summary = "Get out of stock videogames.", description = "Retrieves all video games whose stock is currently zero.")
    @GetMapping("/out-of-stock")
    public ResponseEntity<List<VideoGameResponseDTO>> getGameByStockEquals() {
        return ResponseEntity.ok(videoGameService.findByStockEquals());
    }

}
