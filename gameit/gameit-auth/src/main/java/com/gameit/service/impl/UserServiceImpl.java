package com.gameit.service.impl;

import com.gameit.model.Authority;
import com.gameit.model.User;
import com.gameit.model.exceptions.EmailExistsException;
import com.gameit.repository.AuthorityRepository;
import com.gameit.repository.UserRepository;
import com.gameit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User getLoggedInUser() {
        String username = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        User user = userRepository.findByEmail(username);
        return user;
    }

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
    public User create(User user, ArrayList<Authority> roles) throws EmailExistsException {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new EmailExistsException();
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAuthorities(roles.stream()
                .map(authority -> authorityRepository.findOne(authority.getName()))
                .collect(Collectors.toSet()));
        user = userRepository.save(user);

        return user;
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

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsernameCaseInsensitive(username);
    }
}
