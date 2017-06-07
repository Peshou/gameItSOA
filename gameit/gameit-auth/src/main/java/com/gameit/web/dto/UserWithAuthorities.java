package com.gameit.web.dto;

import com.gameit.model.User;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class UserWithAuthorities {
    @NotNull
    private User user;
    private List<String> authorities;

    public UserWithAuthorities(User user, List<String> authorities) {
        this.user = user;
        this.authorities = authorities;
    }

    public UserWithAuthorities() {
        authorities = new ArrayList<>();
    }

    public User getUser() {
        return user;
    }

    public List<String> getAuthorities() {
        return authorities;
    }
}
