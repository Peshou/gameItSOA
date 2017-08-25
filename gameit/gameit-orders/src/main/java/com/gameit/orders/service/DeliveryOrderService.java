package com.gameit.orders.service;

import com.gameit.orders.model.DeliveryOrder;
import com.gameit.orders.model.DeliveryOrderStatus;

public interface DeliveryOrderService {
    DeliveryOrder findOne(String id);

    DeliveryOrder changeStatus(DeliveryOrder deliveryOrder, DeliveryOrderStatus newStatus);
}
