package com.gon.store.service;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.gon.store.dto.DeveloperRequestDTO;
import com.gon.store.dto.DeveloperResponseDTO;
import com.gon.store.entity.Developer;
import com.gon.store.entity.VideoGame;
import com.gon.store.exception.EntityNotFoundException;
import com.gon.store.repository.DeveloperRepository;

@Service 
public class DeveloperService {
    private final DeveloperRepository developerRepository;

    private DeveloperService(DeveloperRepository developerRepository){
        this.developerRepository = developerRepository;
    }

    public DeveloperResponseDTO createDeveloper(DeveloperRequestDTO developer){
        Developer dev = developerRepository.save(toEntity(developer));
        return toResponse(dev);
    }

    public DeveloperResponseDTO findById(Long id){
        Developer dev = developerRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Developer", id));

        return toResponse(dev);
    }

    public List<DeveloperResponseDTO> findAll(){
        return developerRepository.findAll()
            .stream()
            .map(this::toResponse)
            .toList();
    }

    public void updateDeveloper(Long id, DeveloperRequestDTO developer){
        Developer dev = developerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Developer",id));

        dev.setName(developer.name());
        dev.setCountry(developer.country());
        dev.setWebsite(developer.website());

        developerRepository.save(dev);
    }

    private Developer toEntity(DeveloperRequestDTO developer){
        return new Developer(
            null, 
            developer.name(), 
            developer.country(), 
            developer.website(), 
            null);
    }

    private DeveloperResponseDTO toResponse(Developer developer){
        List<String> gameTitles = developer.getGames() != null 
            ? developer.getGames().stream().map(VideoGame::getTitle).toList()
            : Collections.emptyList();
        
        return new DeveloperResponseDTO(
            developer.getId(),
            developer.getName(),
            developer.getCountry(),
            developer.getWebsite(),
            gameTitles
        );
    }
}
