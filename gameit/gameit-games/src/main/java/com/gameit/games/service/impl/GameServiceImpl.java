package com.gameit.games.service.impl;

import com.gameit.games.model.Game;
import com.gameit.games.model.User;
import com.gameit.games.repository.GameRepository;
import com.gameit.games.service.GameService;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

@Service
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;

    private final DiscoveryClient discoveryClient;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository, DiscoveryClient discoveryClient) {
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
    public Game create(Game game) throws Exception {
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
            this.restTemplate.exchange("http://" + authService.getInstanceInfo().getIPAddr() + ":8080/users/" + seller.getId(), HttpMethod.PUT, userEntity, User.class);

            return game;
        }

        throw new Exception();
    }

    @Override
    @Transactional
    public Game update(Game updatedGame) {
        Game game = gameRepository.findOne(updatedGame.getId());
        game.setCategory(updatedGame.getCategory());
        game.setDescription(updatedGame.getDescription());
        game.setDiscountPercent(updatedGame.getDiscountPercent());
        game.setReleaseYear(updatedGame.getReleaseYear());
        game.setName(updatedGame.getName());
        game.setGamePrice(updatedGame.getGamePrice());
        game.setItemsLeft(updatedGame.getItemsLeft());
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
        if (file != null) {
            try {
                pictureBlob = new SerialBlob(file.getBytes());
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }

            game.setPreviewImage(pictureBlob);
            gameRepository.save(game);
        }
    }
}

