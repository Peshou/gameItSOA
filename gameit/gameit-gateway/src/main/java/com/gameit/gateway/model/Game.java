package com.gameit.gateway.model;

import java.util.HashSet;
import java.util.Set;

public class Game {
    private String id;

    private String name;

    private Integer releaseYear;

    private String description;

    private String category;

    private Double gamePrice;

    private String previewImage;

    private Long itemsLeft;

    private Integer discountPercent;

    private Set<UserGameOrder> userGameOrders = new HashSet<UserGameOrder>(0);

    private User userSeller;

    private String previewImageToString;

    private Long createdAt;

    private Long updatedAt;

    public Game() {

    }

    public Game(Game game) {
        this.name = game.name;
        this.releaseYear = game.releaseYear;
        this.description = game.description;
        this.itemsLeft = game.itemsLeft;
        this.category = game.category;
        this.previewImage = game.previewImage;
        this.previewImageToString = game.previewImageToString;
        this.id = game.id;
        this.updatedAt = game.updatedAt;
        this.createdAt = game.createdAt;
        this.discountPercent = game.discountPercent;
        this.userSeller = game.userSeller;
        this.userGameOrders = game.userGameOrders;
    }


    public Game(String name, Integer releaseYear, String description, Long itemsLeft, Integer discountPercent) {
        this.name = name;
        this.releaseYear = releaseYear;

        this.description = description;
        this.itemsLeft = itemsLeft;
        this.discountPercent = discountPercent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
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

    public String getPreviewImage() {
        return previewImage;
    }

    public void setPreviewImage(String previewImage) {
        this.previewImage = previewImage;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public void setPreviewImageToString(String previewImageToString) {
        this.previewImageToString = previewImageToString;
    }

    public String getPreviewImageToString() {
        return this.previewImageToString;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", releaseYear=" + releaseYear +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", gamePrice=" + gamePrice +
                ", previewImage='" + previewImage + '\'' +
                ", previewImagez='" + previewImageToString + '\'' +
                ", itemsLeft=" + itemsLeft +
                ", discountPercent=" + discountPercent +
                ", userGameOrders=" + userGameOrders +
                ", userSeller=" + userSeller +
                ", previewImageToString='" + previewImageToString + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
