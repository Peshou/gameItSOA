package com.gameit.service;

import com.stripe.exception.*;

public interface PaymentProcessorService {
    String createChargeRequest(String paymentToken, Double price, String productName) throws CardException, APIException, AuthenticationException, InvalidRequestException, APIConnectionException;
}
