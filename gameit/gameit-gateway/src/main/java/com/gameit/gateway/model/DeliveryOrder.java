package com.gameit.gateway.model;

public class DeliveryOrder {

    private String id;

    private UserGameOrder gameOrder;

    private DeliveryOrderStatus status;

    private String description;

    private Address shippingAddress;

    private Long createdAt;

    private Long updatedAt;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
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

    public DeliveryOrderStatus getStatus() {
        return status;
    }

    public void setStatus(DeliveryOrderStatus status) {
        this.status = status;
    }

    public UserGameOrder getGameOrder() {
        return gameOrder;
    }

    public void setGameOrder(UserGameOrder gameOrder) {
        this.gameOrder = gameOrder;
    }
}
