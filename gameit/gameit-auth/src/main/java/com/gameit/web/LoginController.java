package com.gameit.web;

import com.gameit.model.User;
import com.gameit.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    @Autowired
    private UserService userService;


    @GetMapping("/me")
    public User getLoggedInUserDetails() {
        String name = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();

        if (name.contains("@")) {
            return userService.findByEmail(name);
        } else{
            return userService.findByUsername(name);
        }
    }


}
