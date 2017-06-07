package com.gameit.service;

import com.gameit.model.Authority;
import com.gameit.model.User;
import com.gameit.model.exceptions.EmailExistsException;

import java.util.ArrayList;
import java.util.List;

public interface UserService {
    User findByEmail(String email);

    User findById(String id);

    User update(User beforeUpdate, User user);

    void delete(String id);

    List<User> findAll();
}
