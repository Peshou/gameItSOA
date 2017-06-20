package com.gameit.web.dto;

import com.gameit.model.Game;
import com.gameit.model.User;

public class StripeBuyerToken {
    private String token;
    private User buyer;
    private Game game;

    public StripeBuyerToken() {
    }

    public StripeBuyerToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public User getBuyer() {
        return buyer;
    }

    public Game getGame() {
        return game;
    }

}
