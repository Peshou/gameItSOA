package com.gameit.config;

import com.gameit.model.Authority;
import com.gameit.model.User;
import com.gameit.model.exceptions.EmailExistsException;
import com.gameit.repository.AuthorityRepository;
import com.gameit.security.Authorities;
import com.gameit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Stefan on 06.4.2017.
 */
@Service
public class DataInit {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    PasswordEncoder encoder;

    @PostConstruct
    public void init() throws IOException, SQLException {
        Authority adminAuthority = authorityRepository.findOne(Authorities.ROLE_ADMIN.name());
        if(adminAuthority == null) {
            adminAuthority = new Authority();
            adminAuthority.setName(Authorities.ROLE_ADMIN.name());
            adminAuthority = authorityRepository.save(adminAuthority);
        }

        Authority sellerAuthority = authorityRepository.findOne(Authorities.ROLE_SELLER.name());
        if(sellerAuthority == null) {
            sellerAuthority = new Authority();
            sellerAuthority.setName(Authorities.ROLE_SELLER.name());
            sellerAuthority = authorityRepository.save(sellerAuthority);
        }

        Authority buyerAuthority = authorityRepository.findOne(Authorities.ROLE_BUYER.name());
        if(buyerAuthority == null) {
            buyerAuthority = new Authority();
            buyerAuthority.setName(Authorities.ROLE_BUYER.name());
            buyerAuthority = authorityRepository.save(buyerAuthority);
        }

        User admin = userService.findByEmail("admin@example.com");
        if (admin == null) {
            admin = new User();
            admin.setUsername("admin");
            admin.setPassword("admin");
            admin.setEmail("admin@example.com");

            ArrayList<Authority> roles = new ArrayList<>();
            roles.add(adminAuthority);

            try {
                userService.create(admin, roles);
            } catch (EmailExistsException e) {
                e.printStackTrace();
            }
        }

        User user = userService.findByEmail("user@example.com");
        if (user == null) {
            user = new User();
            user.setPassword("user");
            user.setUsername("user");
            user.setEmail("user@example.com");

            ArrayList<Authority> roles = new ArrayList<>();
            roles.add(buyerAuthority);

            try {
                userService.create(user, roles);
            } catch (EmailExistsException e) {
                e.printStackTrace();
            }
        }

        User stefan = userService.findByEmail("stefan@example.com");
        if (stefan == null) {
            stefan = new User();
            stefan.setUsername("stefan");
            stefan.setPassword("stefan");
            stefan.setEmail("stefan@example.com");

            ArrayList<Authority> roles = new ArrayList<>();
            roles.add(sellerAuthority);

            try {
                userService.create(stefan, roles);
            } catch (EmailExistsException e) {
                e.printStackTrace();
            }
        }

    }
}
