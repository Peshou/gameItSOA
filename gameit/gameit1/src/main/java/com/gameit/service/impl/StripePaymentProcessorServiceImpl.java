package com.gameit.service.impl;

//import com.gameit.config.GameitProperties;
import com.gameit.service.PaymentProcessorService;
import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
//@EnableConfigurationProperties(GameitProperties.class)
public class StripePaymentProcessorServiceImpl implements PaymentProcessorService {
//    @Autowired
//    private GameitProperties gameitProperties;

    @Override
    public String createChargeRequest(String paymentToken, Double price, String productName) throws CardException, APIException, AuthenticationException, InvalidRequestException, APIConnectionException {
//        Stripe.apiKey = gameitProperties.getStripe().getTestpk();

        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", price.intValue()); // Amount in cents
        chargeParams.put("currency", "usd");
        chargeParams.put("source", paymentToken);
        chargeParams.put("description", "Charge for game " + productName);

        Charge charge = Charge.create(chargeParams);

        return charge.getId();
    }
}
