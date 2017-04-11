package com.gameit.service.impl;

import com.gameit.model.Game;
import com.gameit.model.User;
import com.gameit.model.UserGameOrder;
import com.gameit.repository.UserGameOrderRepository;
import com.gameit.service.UserGameOrderService;
import com.gameit.service.UserService;
import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserGameOrderServiceImpl implements UserGameOrderService {
    @Autowired
    private UserGameOrderRepository userGameOrderRepository;

    @Autowired
    private UserService userService;

    private static final String STRIPE_KEY_PREFIX = "stripe.";
    private static final String TEST_PRIVATE_KEY = "testpk";

    @Autowired
    Environment environment;

    private RelaxedPropertyResolver stripeConfig;

    public void initPropertyResolver() {
        stripeConfig = new RelaxedPropertyResolver(environment, STRIPE_KEY_PREFIX);
    }

    @Override
    public UserGameOrder placeOrder(String stripeToken, Game game) {
        initPropertyResolver();
        Stripe.apiKey = stripeConfig.getProperty(TEST_PRIVATE_KEY);

        User buyer = userService.getLoggedInUser();

        Double price = game.getGamePrice() * 100;

        try {
            Map<String, Object> chargeParams = new HashMap<>();
            chargeParams.put("amount", price.intValue()); // Amount in cents
            chargeParams.put("currency", "usd");
            chargeParams.put("source", stripeToken);
            chargeParams.put("description", "Charge for game " + game.getName());

            Charge charge = Charge.create(chargeParams);

            UserGameOrder userGameOrder = new UserGameOrder();
            userGameOrder.setUser(buyer);
            userGameOrder.setGame(game);
            userGameOrder.setStripeOrderId(charge.getId());

            userGameOrder = userGameOrderRepository.save(userGameOrder);

            return userGameOrder;
        } catch (APIConnectionException | InvalidRequestException | APIException | AuthenticationException | CardException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Page<UserGameOrder> getAllOrders(Pageable pageable) {
        User user = userService.getLoggedInUser();
        return userGameOrderRepository.findAllByUser(pageable, user);
    }

    @Override
    public UserGameOrder findOne(String id) {
        return userGameOrderRepository.findOne(id);
    }
}
