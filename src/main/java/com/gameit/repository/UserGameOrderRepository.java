package com.gameit.repository;

import com.gameit.model.User;
import com.gameit.model.UserGameOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Stefan on 08.4.2017.
 */
public interface UserGameOrderRepository extends JpaRepository<UserGameOrder, String> {
    Page<UserGameOrder> findAllByUser(Pageable pageable, User user);

}
