package com.gameit.orders.repository;

import com.gameit.orders.model.UserGameOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserGameOrderRepository extends JpaRepository<UserGameOrder, String> {
    Page<UserGameOrder> findAllByUserId(Pageable pageable, String userId);

}
