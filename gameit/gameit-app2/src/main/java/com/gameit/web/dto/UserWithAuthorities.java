package com.gameit.web.dto;

import com.gameit.model.User;

import java.util.List;

public class UserWithAuthorities {
    private User user;
    private List<String> authorities;

    public UserWithAuthorities(User user, List<String> authorities) {
        this.user = user;
        this.authorities = authorities;
    }

    public UserWithAuthorities() {
    }

    public User getUser() {
        return user;
    }

    public List<String> getAuthorities() {
        return authorities;
    }
}
