package com.gameit.service.impl;

import com.gameit.model.Game;
import com.gameit.model.User;
import com.gameit.model.UserGameOrder;
import com.gameit.repository.UserGameOrderRepository;
import com.gameit.service.PaymentProcessorService;
import com.gameit.service.UserGameOrderService;
import com.stripe.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserGameOrderServiceImpl implements UserGameOrderService {
    @Autowired
    private UserGameOrderRepository userGameOrderRepository;

    @Autowired
    private PaymentProcessorService paymentProcessorService;

    @Override
    @Transactional
    public UserGameOrder placeOrder(String paymentToken, User buyer, Game game) {

        Double price = (game.getGamePrice() - (game.getGamePrice() * (game.getDiscountPercent() / 100)))*100;
        try {
            String chargeId =  paymentProcessorService.createChargeRequest(paymentToken, price, game.getName());

            UserGameOrder userGameOrder = new UserGameOrder();
            userGameOrder.setUser(buyer);
            userGameOrder.setGame(game);
            userGameOrder.setPaymentProcessorChargeId(chargeId);

            userGameOrder = userGameOrderRepository.save(userGameOrder);
            return userGameOrder;
        } catch (APIConnectionException | InvalidRequestException | APIException | AuthenticationException | CardException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Page<UserGameOrder> getAllOrders(User user, Pageable pageable) {
        return userGameOrderRepository.findAllByUser(pageable, user);
    }

    @Override
    public UserGameOrder findOne(String id) {
        return userGameOrderRepository.findOne(id);
    }
}
