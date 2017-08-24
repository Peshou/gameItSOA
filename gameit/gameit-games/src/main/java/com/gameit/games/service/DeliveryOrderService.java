package com.gameit.games.service;


import com.gameit.games.model.DeliveryOrder;
import com.gameit.games.model.DeliveryOrderStatus;

public interface DeliveryOrderService {
    DeliveryOrder findOne(String id);

    DeliveryOrder changeStatus(DeliveryOrder deliveryOrder, DeliveryOrderStatus newStatus);
}
