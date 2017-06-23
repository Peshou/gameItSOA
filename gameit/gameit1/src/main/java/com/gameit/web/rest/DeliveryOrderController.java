package com.gameit.web.rest;

import com.gameit.model.DeliveryOrder;
import com.gameit.model.DeliveryOrderStatus;
import com.gameit.service.DeliveryOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RepositoryRestController
public class DeliveryOrderController {
    @Autowired
    private DeliveryOrderService deliveryOrderService;

    @PutMapping("/deliveryOrders/{id}/status")
    public ResponseEntity changeDelieryOrderStatus(@PathVariable String id, @RequestBody DeliveryOrderStatus newStatus) {
        DeliveryOrder deliveryOrder = deliveryOrderService.findOne(id);
        deliveryOrder = deliveryOrderService.changeStatus(deliveryOrder, newStatus);

        return ResponseEntity.ok(deliveryOrder);
    }
}
