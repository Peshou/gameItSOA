package com.gameit.service;

import com.gameit.model.Authority;
import com.gameit.model.User;
import com.gameit.model.exceptions.EmailExistsException;

import java.util.ArrayList;

public interface UserService {
    User  getLoggedInUser();

    User findByEmail(String email);

    User findById(String id);

    User create(User user, ArrayList<Authority> roles) throws EmailExistsException;

    User update(User user);

    void delete(String id);
}
