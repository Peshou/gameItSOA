package com.gameit.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Table
@Entity
public class Game extends AbstractBaseEntity {
    @NotNull
    @Column(nullable = false)
    private String name;

    @Column
    private Integer releaseYear;

    @Column(length = 4000)
    private String description;

    @Column
    private Double gamePrice;

    @ElementCollection
    @CollectionTable(name = "game_image_paths")
    private Set<String> imagePaths;

    @Column
    private Long itemsLeft;

    @Column
    private Integer discountPercent;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "game")
    private Set<UserGameOrder> userGameOrders = new HashSet<UserGameOrder>(0);

    @ManyToOne
    @JoinColumn(name = "user_seller")
    private User userSeller;


    public Game() {
        imagePaths = new HashSet<>();
    }

    public Game(String name, Integer releaseYear, String description, Set<String> imagePaths, Long itemsLeft, Integer discountPercent) {
        this.name = name;
        this.releaseYear = releaseYear;
        this.description = description;
        this.imagePaths = imagePaths;
        this.itemsLeft = itemsLeft;
        this.discountPercent = discountPercent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<String> getImagePaths() {
        return imagePaths;
    }

    public void setImagePaths(Set<String> imagePaths) {
        this.imagePaths = imagePaths;
    }

    public Long getItemsLeft() {
        return itemsLeft;
    }

    public void setItemsLeft(Long itemsLeft) {
        this.itemsLeft = itemsLeft;
    }

    public Integer getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(Integer discountPercent) {
        this.discountPercent = discountPercent;
    }

    public Double getGamePrice() {
        return gamePrice;
    }

    public void setGamePrice(Double gamePrice) {
        this.gamePrice = gamePrice;
    }

    public Set<UserGameOrder> getUserGameOrders() {
        return userGameOrders;
    }

    public void setUserGameOrders(Set<UserGameOrder> userGameOrders) {
        this.userGameOrders = userGameOrders;
    }

    public User getUserSeller() {

        return userSeller;
    }

    public void setUserSeller(User userSeller) {
        this.userSeller = userSeller;
    }
}
