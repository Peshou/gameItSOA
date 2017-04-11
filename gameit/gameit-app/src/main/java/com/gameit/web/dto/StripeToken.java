package com.gameit.web.dto;

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
