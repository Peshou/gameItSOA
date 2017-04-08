package com.gameit.service;

import com.gameit.model.Authority;
import com.gameit.model.User;
import com.gameit.model.exceptions.EmailExistsException;

import java.util.ArrayList;

/**
 * Created by Stefan on 06.4.2017.
 */
public interface UserService {
    User  getLoggedInUser();

    User findByEmail(String email);

    User findById(String id);

    User create(User user, ArrayList<Authority> roles) throws EmailExistsException;

    User update(User user);

    void delete(String id);
}
