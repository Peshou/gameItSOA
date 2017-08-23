package com.gameit.service.impl;

import com.gameit.config.StorageProperties;
import com.gameit.model.Game;
import com.gameit.model.User;
import com.gameit.model.exceptions.StorageException;
import com.gameit.model.exceptions.StorageFileNotFoundException;
import com.gameit.repository.GameRepository;
import com.gameit.service.GameService;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

@Service
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;

    private final DiscoveryClient discoveryClient;

    private final Path rootLocation;

    @Autowired
    public GameServiceImpl(StorageProperties properties, GameRepository gameRepository, DiscoveryClient discoveryClient, StorageProperties properties1) {
        this.rootLocation = Paths.get("dsa");
        System.out.println(this.rootLocation);
        this.gameRepository = gameRepository;
        this.discoveryClient = discoveryClient;
    }


    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    RestTemplate restTemplate;

    private EurekaDiscoveryClient.EurekaServiceInstance getService(String serviceName) {
        Random rnd = new Random();
        List<ServiceInstance> services = discoveryClient.getInstances(serviceName);
        return (EurekaDiscoveryClient.EurekaServiceInstance) services.get(rnd.nextInt(services.size()));
    }

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
        EurekaDiscoveryClient.EurekaServiceInstance authService = getService("my-auth");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> entity = new HttpEntity<Object>(null, headers);

        ResponseEntity<User> responseEntity =
                this.restTemplate.exchange("http://" + authService.getInstanceInfo().getIPAddr() + ":8080/me", HttpMethod.GET, entity, User.class);


        User seller = responseEntity.getBody();
        if (seller != null) {
            game.setUserSeller(seller);
            game = gameRepository.save(game);

            seller.getSellingGames().add(game);

            HttpEntity<User> userEntity = new HttpEntity<User>(seller, headers);
            this.restTemplate.exchange("http://" + authService.getInstanceInfo().getIPAddr() + ":8080/users/"+seller.getId(), HttpMethod.PUT, userEntity, User.class);

            return game;
        }

        return null;
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
    @Transactional
    public void store(Game game, MultipartFile file) {
        Blob pictureBlob = null;
        if(file != null) {
            try {
                pictureBlob = new SerialBlob(file.getBytes());
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }

            game.setPreviewImage(pictureBlob);
            gameRepository.save(game);
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
