package com.gameit.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.hateoas.core.Relation;
import org.springframework.security.crypto.codec.Base64;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

@Table
@Entity
@Relation(collectionRelation = "items")
public class Game extends AbstractBaseEntity {
    @NotNull
    @Column(nullable = false)
    private String name;

    @Column
    private Integer releaseYear;

    @Column(length = 4000)
    private String description;

    @NotNull
    @Column(nullable = false)
    private String category;

    @NotNull
    @Column(nullable = false)
    private Double gamePrice;

    @JsonIgnore
    private Blob previewImage;

    @NotNull
    @Column(nullable = false)
    private Long itemsLeft;

    @Column
    private Integer discountPercent;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "game")
    private Set<UserGameOrder> userGameOrders = new HashSet<UserGameOrder>(0);

    @ManyToOne
    @JoinColumn(name = "user_seller")
    private User userSeller;

    private String previewImageToString;

    public Game() {

    }


    public Game(String name, Integer releaseYear, String description, Long itemsLeft, Integer discountPercent) {
        this.name = name;
        this.releaseYear = releaseYear;

        this.description = description;
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

    public Blob getPreviewImage() {
        return previewImage;
    }

    public void setPreviewImage(Blob previewImage) {
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

    public String getPreviewImageToString() {
        String base64Encoded = null;
        if (this.getPreviewImage() != null) {
            byte[] encodeBase64 = new byte[0];
            try {
                encodeBase64 = Base64.encode(this.getPreviewImage().getBytes(1, (int) this.getPreviewImage().length()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                base64Encoded = new String(encodeBase64, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return base64Encoded;
    }

    public void setPreviewImageToString(String previewImageToString) {
        this.previewImageToString = previewImageToString;
    }

    @Override
    public String toString() {
        return "Game{" +
                "name='" + name + '\'' +
                ", releaseYear=" + releaseYear +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", gamePrice=" + gamePrice +
                ", previewImage=" + getPreviewImageToString() +
                ", previewImageToString=" + previewImageToString +
                ", itemsLeft=" + itemsLeft +
                ", discountPercent=" + discountPercent +
                ", userGameOrders=" + userGameOrders +
                ", userSeller=" + userSeller +
                '}';
    }
}
