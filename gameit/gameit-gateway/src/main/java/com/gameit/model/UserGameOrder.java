package com.gameit.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table
public class UserGameOrder extends AbstractBaseEntity {

    @ManyToOne
    @NotNull
    private User user;

    @ManyToOne
    private Game game;

    @Column(nullable = false)
    private String paymentProcessorChargeId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_order_id")
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
}