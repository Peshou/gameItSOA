package com.gameit.games.service;

import com.gameit.games.model.Game;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface GameService {
    Game findById(String id);

    Page<Game> findAll(Pageable pageable);

    Game create(Game game);

    Game update(Game game);

    void delete(String id);

    void store(Game game, MultipartFile file);
}
