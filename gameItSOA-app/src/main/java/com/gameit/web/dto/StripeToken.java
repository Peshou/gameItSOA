package com.gameit.web.dto;

/**
 * Created by Stefan on 08.4.2017.
 */
public class StripeToken {
    private String token;

    public StripeToken() {
    }

    public StripeToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
