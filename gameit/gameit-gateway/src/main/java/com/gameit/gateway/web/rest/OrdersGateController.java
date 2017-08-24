package com.gameit.gateway.web.rest;

import com.gameit.gateway.model.UserGameOrder;
import com.gameit.gateway.web.dto.StripeBuyerToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
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
    public UserGameOrder createCharge(@RequestBody StripeBuyerToken stripeToken) {
        EurekaDiscoveryClient.EurekaServiceInstance gameService = getService("my-orders");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<StripeBuyerToken> entity = new HttpEntity<>(stripeToken, headers);

        ResponseEntity<UserGameOrder> responseEntity =
                this.restTemplate.exchange("http://" + gameService.getInstanceInfo().getIPAddr() + ":8080/games/order", HttpMethod.POST, entity, UserGameOrder.class);
//                this.restTemplate.exchange("http://" + "localhost" + ":8081/games/order", HttpMethod.POST, entity, UserGameOrder.class);


        EurekaDiscoveryClient.EurekaServiceInstance mailService = getService("my-mail");
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UserGameOrder> orderHttpEntity = new HttpEntity<UserGameOrder>(responseEntity.getBody(), headers);
        this.restTemplate.postForEntity("http://" + mailService.getInstanceInfo().getIPAddr() + ":8080/mail/order", orderHttpEntity, null);
//        this.restTemplate.postForEntity("http://" + "localhost" + ":8084/mail/order", orderHttpEntity, null);

        return responseEntity.getBody();
    }

    private EurekaDiscoveryClient.EurekaServiceInstance getService(String serviceName) {
        Random rnd = new Random();
        List<ServiceInstance> services = discoveryClient.getInstances(serviceName);
        return (EurekaDiscoveryClient.EurekaServiceInstance) services.get(rnd.nextInt(services.size()));
    }

}


