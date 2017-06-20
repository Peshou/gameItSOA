package com.gameit;

import com.gameit.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resources;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@RestController
public class GateController {

    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;


    @RequestMapping("/clients")
    public List<ServiceInstance> clients(@RequestParam(value="name") String name) {
        return this.discoveryClient.getInstances(name);
    }

    @RequestMapping("/greeting")
    public String greeting() {
        Random rnd = new Random();

        //my-app1
        List<ServiceInstance> services1 = discoveryClient.getInstances("my-app1");
        EurekaDiscoveryClient.EurekaServiceInstance service1 = (EurekaDiscoveryClient.EurekaServiceInstance) services1.get(rnd.nextInt(services1.size()));
        String ip1 = service1.getInstanceInfo().getIPAddr();
        String greeting1 = this.restTemplate.getForObject("http://"+ip1+":8080/greeting", String.class);

        return String.format("Got my-app1 answer: %s from ip %s", greeting1, ip1);
    }

    @RequestMapping(value = "/games", method = RequestMethod.GET)
    public PagedResources<Game> getGames(@RequestParam(name = "page",required = false) Integer page,@RequestParam(name = "size",required = false) Integer size) {
        EurekaDiscoveryClient.EurekaServiceInstance gameService = getService("my-games");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> entity = new HttpEntity<Object>(null, headers);

        ResponseEntity<PagedResources<Game>> responseEntity =
                this.restTemplate.exchange("http://" + gameService.getInstanceInfo().getIPAddr() + ":8080/games"+"?page="+page+"&size="+size, HttpMethod.GET, entity, new ParameterizedTypeReference<PagedResources<Game>>(){});

        return responseEntity.getBody();
    }

    private EurekaDiscoveryClient.EurekaServiceInstance getService(String serviceName) {
        Random rnd = new Random();
        List<ServiceInstance> services = discoveryClient.getInstances(serviceName);
        return (EurekaDiscoveryClient.EurekaServiceInstance) services.get(rnd.nextInt(services.size()));
    }

}
