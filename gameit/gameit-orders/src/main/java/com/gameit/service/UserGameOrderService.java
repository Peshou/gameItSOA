package com.gameit.service;

import com.gameit.model.Game;
import com.gameit.model.User;
import com.gameit.model.UserGameOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserGameOrderService {
    UserGameOrder placeOrder(String paymentToken, User buyer, Game game);

    Page<UserGameOrder> getAllOrders(User user, Pageable pageable);

    UserGameOrder findOne(String id);
}
