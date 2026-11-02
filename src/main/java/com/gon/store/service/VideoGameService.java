package com.gon.store.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gon.store.dto.PriceBetweenRequestDTO;
import com.gon.store.dto.VideoGameRequestDTO;
import com.gon.store.dto.VideoGameResponseDTO;
import com.gon.store.entity.VideoGame;
import com.gon.store.exception.VideoGameNotFoundException;
import com.gon.store.repository.VideoGameRepository;

@Service
public class VideoGameService {
    private final VideoGameRepository videoGameRepository;

    private VideoGameService(VideoGameRepository videoGameRepository) {
        this.videoGameRepository = videoGameRepository;
    }

    public VideoGameResponseDTO createVideoGame(VideoGameRequestDTO videoGame){
        VideoGame game = videoGameRepository.save(toEntity(videoGame));
        return toResponse(game);
    }

    public VideoGameResponseDTO findById(Long id){
        VideoGame game = videoGameRepository.findById(id)
            .orElseThrow(() -> new VideoGameNotFoundException(id));
        return toResponse(game);
    }

    public List<VideoGameResponseDTO> findAll(){
        return videoGameRepository.findAll().stream().map(this::toResponse).toList();
    }

    public void updateVideoGame(Long id, VideoGameRequestDTO videoGame){
        VideoGame game = videoGameRepository.findById(id)
            .orElseThrow(() -> new VideoGameNotFoundException(id));

        game.setTitle(videoGame.title());
        game.setPlatform(videoGame.platform());
        game.setCategory(videoGame.category());
        game.setPrice(videoGame.price());
        game.setStock(videoGame.stock());
        game.setReleaseDate(videoGame.releaseDate());

        videoGameRepository.save(game);
    }

    public void deleteVideoGame(Long id){
        if (!videoGameRepository.existsById(id)) {
            throw new VideoGameNotFoundException(id);
        }

        videoGameRepository.deleteById(id);
    }

    public List<VideoGameResponseDTO> findByPlatform(String platform){
        return videoGameRepository.findByPlatform(platform).stream()
            .map(this::toResponse)
            .toList();
    }

    public List<VideoGameResponseDTO> findByPriceBetween(PriceBetweenRequestDTO prices){
        if (prices.minPrice().compareTo(prices.maxPrice()) > 0) {
            throw new IllegalArgumentException("El minimo debe ser menor al maximo");
        }
        return videoGameRepository.findByPriceBetween(prices.minPrice(), prices.maxPrice())
            .stream()
            .map(this::toResponse)
            .toList();
    }

    public List<VideoGameResponseDTO> findByStockEquals(){
        return videoGameRepository.findByStockEquals(0)
            .stream()
            .map(this::toResponse)
            .toList();
    }

    private VideoGameResponseDTO toResponse(VideoGame videoGame){
        return new VideoGameResponseDTO(
            videoGame.getId(), 
            videoGame.getTitle(), 
            videoGame.getPlatform(), 
            videoGame.getCategory(), 
            videoGame.getPrice(), 
            videoGame.getStock(), 
            videoGame.getReleaseDate()
        );
    }

    private VideoGame toEntity(VideoGameRequestDTO requestDTO){
        return new VideoGame(
            null,
            requestDTO.title(), 
            requestDTO.platform(), 
            requestDTO.category(), 
            requestDTO.price(), 
            requestDTO.stock(), 
            requestDTO.releaseDate()
        );
    }
}
