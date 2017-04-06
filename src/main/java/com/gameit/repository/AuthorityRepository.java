package com.gameit.repository;

import com.gameit.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Stefan on 06.4.2017.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
