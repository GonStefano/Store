package com.gon.store.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gon.store.dto.PriceBetweenRequestDTO;
import com.gon.store.dto.VideoGameRequestDTO;
import com.gon.store.dto.VideoGameResponseDTO;
import com.gon.store.entity.Developer;
import com.gon.store.entity.VideoGame;
import com.gon.store.exception.EntityNotFoundException;
import com.gon.store.repository.DeveloperRepository;
import com.gon.store.repository.VideoGameRepository;

@Service
public class VideoGameService {
    private final VideoGameRepository videoGameRepository;
    private final DeveloperRepository developerRepository;

    private VideoGameService(VideoGameRepository videoGameRepository, DeveloperRepository developerRepository) {
        this.videoGameRepository = videoGameRepository;
        this.developerRepository = developerRepository;
    }

    public VideoGameResponseDTO createVideoGame(VideoGameRequestDTO videoGame){
        VideoGame game = videoGameRepository.save(toEntity(videoGame));
        return toResponse(game);
    }

    public VideoGameResponseDTO findById(Long id){
        VideoGame game = videoGameRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("VideoGame",id));
        return toResponse(game);
    }

    public  VideoGame findEntityById(Long id){
        VideoGame game = videoGameRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("VideoGame",id));
        return game;
    }

    public List<VideoGameResponseDTO> findAll(){
        return videoGameRepository.findAll().stream().map(this::toResponse).toList();
    }

    public void updateVideoGame(Long id, VideoGameRequestDTO videoGame){
        VideoGame game = findEntityById(id);

        Developer developer = developerRepository.findById(videoGame.developerId()).orElseThrow(() -> new EntityNotFoundException("Developer",videoGame.developerId()));

        game.setTitle(videoGame.title());
        game.setPlatform(videoGame.platform());
        game.setCategory(videoGame.category());
        game.setPrice(videoGame.price());
        game.setStock(videoGame.stock());
        game.setReleaseDate(videoGame.releaseDate());
        game.setDeveloper(developer);

        videoGameRepository.save(game);
    }

    public void deleteVideoGame(Long id){
        if (!videoGameRepository.existsById(id)) {
            throw new EntityNotFoundException("VideoGame",id);
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
            throw new IllegalArgumentException("Minimum price must be less than or equal to maximum price");
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
            videoGame.getReleaseDate(),
            videoGame.getDeveloper() != null ? videoGame.getDeveloper().getId() : null
        );
    }

    private VideoGame toEntity(VideoGameRequestDTO requestDTO){
        Developer developer = developerRepository.findById(requestDTO.developerId()).orElseThrow(() -> new EntityNotFoundException("Developer",requestDTO.developerId()));
        VideoGame videoGame = new VideoGame(
            null,
            requestDTO.title(), 
            requestDTO.platform(), 
            requestDTO.category(), 
            requestDTO.price(), 
            requestDTO.stock(), 
            requestDTO.releaseDate()
        );
        videoGame.setDeveloper(developer);
        return videoGame;
    }


}
