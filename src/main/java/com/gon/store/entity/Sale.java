package com.gon.store.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "SALE")
public class Sale {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "SALE_DATE", nullable = false)
    private LocalDateTime saleDate;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID", nullable = false)
    private Customer customer;

    @ManyToMany
    @JoinTable(
        name = "SALES_VIDEOGAME",
        joinColumns = @JoinColumn(name = "SALE_ID", referencedColumnName = "ID"),
        inverseJoinColumns = @JoinColumn (name = "VIDEOGAMES_ID", referencedColumnName = "ID")
    )
    private List<VideoGame> videoGames;

    @Column(name = "TOTAL", nullable = false)
    private BigDecimal total;

    @PrePersist
    public void onPrePersist(){
        if (this.saleDate == null) {
            saleDate = LocalDateTime.now();
        }

        calculateTotal();
    }

    public Sale() {
    }

    public Sale(Long id, LocalDateTime saleDate, Customer customer, List<VideoGame> videoGames, BigDecimal total) {
        this.id = id;
        this.saleDate = saleDate;
        this.customer = customer;
        this.videoGames = videoGames;
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDateTime saleDate) {
        this.saleDate = saleDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<VideoGame> getVideoGames() {
        return videoGames;
    }

    public void setVideoGames(List<VideoGame> videoGames) {
        this.videoGames = videoGames;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public void calculateTotal(){
        if (this.videoGames != null && !this.videoGames.isEmpty()) {
            this.total = this.videoGames.stream()
                .map(VideoGame::getPrice)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        } else {
            this.total = BigDecimal.ZERO;
        }
    }
}
