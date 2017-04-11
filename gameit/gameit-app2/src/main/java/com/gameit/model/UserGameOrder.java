package com.gameit.model;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table
public class UserGameOrder extends AbstractBaseEntity {

    @JsonIgnore
    @ManyToOne
    @NotNull
    private User user;

    @ManyToOne
    private Game game;

    @Column(nullable = false)
    private String stripeOrderId;

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

    public String getStripeOrderId() {
        return stripeOrderId;
    }

    public void setStripeOrderId(String stripeOrderId) {
        this.stripeOrderId = stripeOrderId;
    }
}