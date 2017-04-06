package com.gameit.security;

import com.gameit.model.User;
import com.gameit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Created by Stefan on 06.4.2017.
 */
@Component("userDetailsService")
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String emailDetails) throws UsernameNotFoundException {
        String emailDetailsLowercase = emailDetails.toLowerCase();

        User userFromDatabase = userRepository.findByEmail(emailDetailsLowercase);
        if (userFromDatabase == null) {
            throw new UsernameNotFoundException("The user with email " + emailDetailsLowercase + " was not found");
        }

        Collection<GrantedAuthority> grantedAuthorities = userFromDatabase.getAuthorities()
                .stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(userFromDatabase.getEmail(), userFromDatabase.getPassword(), grantedAuthorities);
    }
}
