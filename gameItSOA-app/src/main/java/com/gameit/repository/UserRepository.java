package com.gameit.repository;

import com.gameit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Stefan on 06.4.2017.
 */
public interface UserRepository extends JpaRepository<User, String> {

    @Query("SELECT user FROM User user WHERE LOWER(user.email) = LOWER(:email)")
    User findByEmail(@Param("email") String email);
}
