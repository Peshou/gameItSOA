package com.gameit.service;

import com.gameit.model.Game;
import com.gameit.model.UserGameOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by Stefan on 08.4.2017.
 */
public interface UserGameOrderService {
    UserGameOrder placeOrder(String stripeToken, Game game);

    Page<UserGameOrder> getAllOrders(Pageable pageable);

    UserGameOrder findOne(String id);
}
