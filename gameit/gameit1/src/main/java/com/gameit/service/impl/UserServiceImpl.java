package com.gameit.service.impl;

import com.gameit.model.User;
import com.gameit.repository.AuthorityRepository;
import com.gameit.repository.UserRepository;
import com.gameit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email.toLowerCase());
    }

    @Override
    public User findById(String id) {
        return userRepository.findOne(id);
    }

    @Override
    @Transactional
    public User update(User beforeUpdate, User user) {
        if(user.getUsername()!=null) {
            beforeUpdate.setUsername(user.getUsername());
        }

        return userRepository.save(beforeUpdate);
    }

    @Override
    @Transactional
    public void delete(String id) {
        userRepository.delete(id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

}
