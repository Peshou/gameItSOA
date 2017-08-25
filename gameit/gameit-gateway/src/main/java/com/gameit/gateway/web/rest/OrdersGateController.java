package com.gameit.gateway.web.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gameit.gateway.model.CustomPageImpl;
import com.gameit.gateway.model.Game;
import com.gameit.gateway.model.User;
import com.gameit.gateway.model.UserGameOrder;
import com.gameit.gateway.web.dto.StripeBuyerToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
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
    public UserGameOrder createCharge(@RequestBody StripeBuyerToken stripeToken) {
        EurekaDiscoveryClient.EurekaServiceInstance ordersService = getService("my-orders");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<StripeBuyerToken> entity = new HttpEntity<>(stripeToken, headers);

        ResponseEntity<UserGameOrder> responseEntity =
                this.restTemplate.exchange("http://" + ordersService.getInstanceInfo().getIPAddr() + ":8080/games/order", HttpMethod.POST, entity, UserGameOrder.class);
//                this.restTemplate.exchange("http://" + "localhost" + ":8081/games/order", HttpMethod.POST, entity, UserGameOrder.class);


        EurekaDiscoveryClient.EurekaServiceInstance mailService = getService("my-mail");
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UserGameOrder> orderHttpEntity = new HttpEntity<UserGameOrder>(responseEntity.getBody(), headers);
        this.restTemplate.postForEntity("http://" + mailService.getInstanceInfo().getIPAddr() + ":8080/mail/order", orderHttpEntity, null);
//        this.restTemplate.postForEntity("http://" + "localhost" + ":8084/mail/order", orderHttpEntity, null);

        return responseEntity.getBody();
    }

    @RequestMapping(value = "/orders/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public CustomPageImpl<UserGameOrder> getOrdersForUser(@PathVariable String id, @RequestParam(name = "page", required = true) Integer page, @RequestParam(name = "size", required = true) Integer size) {
        EurekaDiscoveryClient.EurekaServiceInstance ordersService = getService("my-orders");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> entity = new HttpEntity<Object>(null, headers);


        ResponseEntity<CustomPageImpl<UserGameOrder>> responseEntity =
                this.restTemplate.exchange("http://" + ordersService.getInstanceInfo().getIPAddr() + ":8080/orders/" + id + "?page=" + page + "&size=" + size, HttpMethod.GET, entity, new ParameterizedTypeReference<CustomPageImpl<UserGameOrder>>() {
                });

        return responseEntity.getBody();
    }

    private EurekaDiscoveryClient.EurekaServiceInstance getService(String serviceName) {
        Random rnd = new Random();
        List<ServiceInstance> services = discoveryClient.getInstances(serviceName);
        if (services.size() > 0) {
            return (EurekaDiscoveryClient.EurekaServiceInstance) services.get(rnd.nextInt(services.size()));
        } else {
            return null;
        }
    }

}



