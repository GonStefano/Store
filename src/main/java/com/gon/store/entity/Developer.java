package com.gon.store.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity 
@Table(name = "DEVELOPER")
public class Developer {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "NAME", nullable = false, length = 255)
    private String name;
    @Column(name = "COUNTRY", nullable = false, length = 255)
    private String country;
    @Column(name = "WEBSITE", nullable = false, length = 255)
    private String website;
    @Column(name = "GAMES", nullable = true)
    @OneToMany(mappedBy = "developer")
    private List<VideoGame> games;

    public Developer(){}

    public Developer(Long id, String name, String country, String website, List<VideoGame> games) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.website = website;
        this.games = games;
    }

    public Long getId() {
        return id;
    }

    
    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getCountry() {
        return country;
    }


    public void setCountry(String country) {
        this.country = country;
    }


    public String getWebsite() {
        return website;
    }


    public void setWebsite(String website) {
        this.website = website;
    }


    public List<VideoGame> getGames() {
        return games;
    }


    public void setGames(List<VideoGame> games) {
        this.games = games;
    }

    
    
}
