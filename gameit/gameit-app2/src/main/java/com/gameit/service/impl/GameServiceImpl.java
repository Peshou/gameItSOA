package com.gameit.service.impl;

import com.gameit.config.StorageProperties;
import com.gameit.model.Game;
import com.gameit.model.User;
import com.gameit.model.exceptions.StorageException;
import com.gameit.model.exceptions.StorageFileNotFoundException;
import com.gameit.repository.GameRepository;
import com.gameit.repository.UserRepository;
import com.gameit.service.GameService;
import com.gameit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;

    private final UserRepository userRepository;

    private final Path rootLocation;

    @Autowired
    public GameServiceImpl(StorageProperties properties, GameRepository gameRepository, UserRepository userRepository, UserService userService) {
        this.rootLocation = Paths.get(properties.getLocation());
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    private final UserService userService;

    @Override
    public Game findById(String id) {
        return gameRepository.findOne(id);
    }

    @Override
    public Page<Game> findAll(Pageable pageable) {
        return gameRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Game create(Game game) {
        User seller = userService.getLoggedInUser();
        game.setUserSeller(seller);

        game = gameRepository.save(game);

        if(seller != null) {
            seller.getSellingGames().add(game);
            userRepository.save(seller);
        }

        return game;
    }

    @Override
    @Transactional
    public Game update(Game game) {
        return gameRepository.save(game);
    }

    @Override
    @Transactional
    public void delete(String id) {
        gameRepository.delete(id);
    }

    @Override
    public void store(Game game, MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
            }

            String storagePath = "games/" + game.getId() + "/images/";
            if (!Files.exists(rootLocation.resolve(storagePath))) {
                Files.createDirectories(rootLocation.resolve(storagePath));
            }

            //Check if filename already exists so we save the image with a new file name
            Long fileNumberCounter = 0L;
            String fileName = storagePath + file.getOriginalFilename();
            while (Files.exists(this.rootLocation.resolve(fileName))) {
                fileNumberCounter++;
                if (fileNumberCounter > 1) {
                    fileName = fileName.replace("_(" + (fileNumberCounter - 1) + ").", "_(" + fileNumberCounter.toString() + ").");
                } else {
                    fileName = fileName.replace(".", "_(" + fileNumberCounter.toString() + ").");
                }
            }

            Files.copy(file.getInputStream(), this.rootLocation.resolve(fileName));

            game.getImagePaths().add(fileName);
            gameRepository.save(game);
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }

    @Override
    public Path loadPath(String id, String filename) {
        String storagePath = "games/" + id + "/images/";
        return rootLocation.resolve(storagePath + filename);
    }

    @Override
    public Resource loadAsResource(String id, String filename) {
        try {
            Path file = loadPath(id, filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException("Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAllResources() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void initStorage() {
        try {
            Files.createDirectory(rootLocation);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
