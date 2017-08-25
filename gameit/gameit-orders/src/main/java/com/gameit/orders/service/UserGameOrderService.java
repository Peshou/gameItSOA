package com.gameit.orders.service;

import com.gameit.orders.model.Game;
import com.gameit.orders.model.User;
import com.gameit.orders.model.UserGameOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserGameOrderService {
    UserGameOrder placeOrder(String paymentToken, User buyer, Game game);

    Page<UserGameOrder> getAllOrders(String userId, Pageable pageable);

    UserGameOrder findOne(String id);
}
