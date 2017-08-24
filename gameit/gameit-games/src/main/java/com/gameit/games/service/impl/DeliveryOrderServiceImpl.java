package com.gameit.games.service.impl;

import com.gameit.games.model.DeliveryOrder;
import com.gameit.games.model.DeliveryOrderStatus;
import com.gameit.games.repository.DeliveryOrderRepository;
import com.gameit.games.service.DeliveryOrderService;
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
