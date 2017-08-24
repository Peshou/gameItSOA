package com.gameit.gateway.model;

public class UserGameOrder {

    private String id;

    private User user;

    private Game game;

    private String paymentProcessorChargeId;

    private Long createdAt;

    private Long updatedAt;

    private DeliveryOrder deliveryOrder;

    public UserGameOrder() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getPaymentProcessorChargeId() {
        return paymentProcessorChargeId;
    }

    public void setPaymentProcessorChargeId(String paymentProcessorChargeId) {
        this.paymentProcessorChargeId = paymentProcessorChargeId;
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
}