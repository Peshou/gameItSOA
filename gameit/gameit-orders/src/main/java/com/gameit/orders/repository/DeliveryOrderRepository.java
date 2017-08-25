package com.gameit.orders.repository;

import com.gameit.orders.model.DeliveryOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryOrderRepository extends JpaRepository<DeliveryOrder, String> {

}
