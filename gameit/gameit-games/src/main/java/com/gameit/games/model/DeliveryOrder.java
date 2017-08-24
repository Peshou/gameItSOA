package com.gameit.games.model;

import javax.persistence.*;

@Entity
@Table
public class DeliveryOrder extends AbstractBaseEntity {

    @OneToOne(mappedBy = "deliveryOrder")
    private UserGameOrder gameOrder;

    @Enumerated(EnumType.ORDINAL)
    private DeliveryOrderStatus status;

    @Column(length = 4000)
    private String description;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private Address shippingAddress;

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
