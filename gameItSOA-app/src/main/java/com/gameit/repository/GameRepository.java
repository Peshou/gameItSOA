package com.gameit.repository;

import com.gameit.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Stefan on 06.4.2017.
 */
public interface GameRepository extends JpaRepository<Game, String> {
}
