package com.gameit.orders.service.impl;

import com.gameit.orders.model.DeliveryOrder;
import com.gameit.orders.model.DeliveryOrderStatus;
import com.gameit.orders.repository.DeliveryOrderRepository;
import com.gameit.orders.service.DeliveryOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryOrderServiceImpl implements DeliveryOrderService {
    @Autowired
    private DeliveryOrderRepository deliveryOrderRepository;

    @Override
    public DeliveryOrder findOne(String id) {
        return deliveryOrderRepository.findOne(id);
    }

    @Override
    public DeliveryOrder changeStatus(DeliveryOrder deliveryOrder, DeliveryOrderStatus newStatus) {
        deliveryOrder.setStatus(DeliveryOrderStatus.valueOf(newStatus.toString()));

        return deliveryOrderRepository.save(deliveryOrder);
    }
}
