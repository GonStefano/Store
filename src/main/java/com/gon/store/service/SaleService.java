package com.gon.store.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gon.store.dto.SaleRequestDTO;
import com.gon.store.dto.SaleResponseDTO;
import com.gon.store.entity.Customer;
import com.gon.store.entity.Sale;
import com.gon.store.entity.VideoGame;
import com.gon.store.exception.EntityNotFoundException;
import com.gon.store.repository.SaleRepository;
import com.gon.store.repository.VideoGameRepository;

@Service 
public class SaleService {
    private final SaleRepository saleRepository;
    private final VideoGameRepository videoGameRepository;
    private final CustomerService customerService;
    private final VideoGameService videoGameService;

    public SaleService(SaleRepository saleRepository, VideoGameRepository videoGameRepository, CustomerService customerService, VideoGameService videoGameService){
        this.saleRepository = saleRepository;
        this.videoGameRepository = videoGameRepository;
        this.customerService = customerService;
        this.videoGameService = videoGameService;
    }

    public SaleResponseDTO findById(Long id){
        Sale sale = saleRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Sale",id));

        return toResponse(sale);
    }

    @Transactional 
    public SaleResponseDTO createSale(SaleRequestDTO saleRequestDTO){
        Customer customer = customerService.findEntityById(saleRequestDTO.customerId());

        List<VideoGame> videoGames = saleRequestDTO.videoGamesId()
            .stream()
            .map(videoGameService::findEntityById)
            .toList();

        for(VideoGame videoGame : videoGames){
            if (videoGame.getStock() == null || videoGame.getStock() <= 0) {
                throw new IllegalArgumentException("There is not enough stock for the game: " + videoGame.getTitle());
            }

            videoGameRepository.decrementStock(videoGame.getId());
        }

        Sale sale = new Sale();
        sale.setCustomer(customer);
        sale.setVideoGames(videoGames);

        saleRepository.save(sale);

        return new SaleResponseDTO(
            sale.getId(), 
            sale.getSaleDate(), 
            sale.getCustomer().getName(), 
            sale.getVideoGames()
                .stream()
                .map(VideoGame::getTitle)
                .toList(), 
            sale.getTotal());
    }
    
    private SaleResponseDTO toResponse(Sale sale){
        return new SaleResponseDTO(
            sale.getId(),
            sale.getSaleDate(),
            sale.getCustomer().getName(),
            sale.getVideoGames()
                .stream()
                .map(VideoGame::getTitle)
                .toList(),
            sale.getTotal()
        );
    }
}
