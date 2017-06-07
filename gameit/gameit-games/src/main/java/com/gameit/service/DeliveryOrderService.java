package com.gameit.service;

import com.gameit.model.DeliveryOrder;
import com.gameit.model.DeliveryOrderStatus;

public interface DeliveryOrderService {
    DeliveryOrder findOne(String id);

    DeliveryOrder changeStatus(DeliveryOrder deliveryOrder, DeliveryOrderStatus newStatus);
}
