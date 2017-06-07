package com.gameit.web.dto;

public class StripeBuyerToken {
    private String token;

    public String getBuyer() {
        return buyer;
    }

    private String buyer;

    public StripeBuyerToken() {
    }

    public StripeBuyerToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
