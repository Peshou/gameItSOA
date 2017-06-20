package com.gameit.web.rest;

import com.gameit.model.Game;
import com.gameit.model.UserGameOrder;
import com.gameit.web.dto.StripeBuyerToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Random;

@RestController
public class OrdersGateController {

    @Autowired
    private
    RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @PostMapping("/games/order")
    public Resource<UserGameOrder> createCharge(@RequestBody StripeBuyerToken stripeToken) {
        EurekaDiscoveryClient.EurekaServiceInstance gameService = getService("my-orders");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<StripeBuyerToken> entity = new HttpEntity<>(stripeToken, headers);

        ResponseEntity<Resource<UserGameOrder>> responseEntity =
                this.restTemplate.exchange("http://" + gameService.getInstanceInfo().getIPAddr() + ":8080/games/order", HttpMethod.POST, entity, new ParameterizedTypeReference<Resource<UserGameOrder>>() {
                });

        return responseEntity.getBody();
    }

    private EurekaDiscoveryClient.EurekaServiceInstance getService(String serviceName) {
        Random rnd = new Random();
        List<ServiceInstance> services = discoveryClient.getInstances(serviceName);
        return (EurekaDiscoveryClient.EurekaServiceInstance) services.get(rnd.nextInt(services.size()));
    }

}



