package com.gameit.gateway.web.rest;

import com.gameit.gateway.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Random;

@RestController
public class UsersGateController {

    @Autowired
    private
    RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @PutMapping("/users/{userId}")
    public User updateUser(@PathVariable String userId, @RequestBody User user) {
        EurekaDiscoveryClient.EurekaServiceInstance gameService = getService("my-auth");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> entity = new HttpEntity<>(user, headers);

        ResponseEntity<User> responseEntity =
                this.restTemplate.exchange("http://" + gameService.getInstanceInfo().getIPAddr() + ":8080/users/"+userId, HttpMethod.PUT, entity, User.class);
//                this.restTemplate.exchange("http://" + "localhost" + ":9999/users/"+userId, HttpMethod.PUT, entity, User.class);

        return responseEntity.getBody();
    }

    private EurekaDiscoveryClient.EurekaServiceInstance getService(String serviceName) {
        Random rnd = new Random();
        List<ServiceInstance> services = discoveryClient.getInstances(serviceName);
        return (EurekaDiscoveryClient.EurekaServiceInstance) services.get(rnd.nextInt(services.size()));
    }

}



