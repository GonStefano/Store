package com.gon.store.service;

import java.util.List;
import java.util.Optional;



import org.springframework.stereotype.Service;


import com.gon.store.dto.VideoGameRequestDTO;
import com.gon.store.dto.VideoGameResponseDTO;
import com.gon.store.entity.VideoGame;
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

    public Optional<VideoGameResponseDTO> findById(Long id){
        return videoGameRepository.findById(id)
        .map(this::toResponse);
    }

    public List<VideoGameResponseDTO> findAll(){
        return videoGameRepository.findAll().stream().map(this::toResponse).toList();
    }

    public boolean updateVideoGame(Long id, VideoGameRequestDTO videoGame){
        Optional<VideoGame> isExistingVideoGame = videoGameRepository.findById(id);
        if(isExistingVideoGame.isEmpty()){
            return false;
        }

        VideoGame updateVideoGame = isExistingVideoGame.get();
        updateVideoGame.setTitle(videoGame.title());
        updateVideoGame.setPlatform(videoGame.platform());
        updateVideoGame.setCategory(videoGame.category());
        updateVideoGame.setPrice(videoGame.price());
        updateVideoGame.setStock(videoGame.stock());
        updateVideoGame.setReleaseDate(videoGame.releaseDate());

        videoGameRepository.save(updateVideoGame);
        return true;
    }

    public boolean deleteVideoGame(Long id){
        Optional<VideoGame> optionalVideoGame = videoGameRepository.findById(id);

        if (optionalVideoGame.isPresent()) {
            videoGameRepository.deleteById(id);
            return true;
        }else{
            return false;
        }
    }

    public List<VideoGameResponseDTO> findByPlatform(String platform){
        return videoGameRepository.findByPlatform(platform).stream()
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
