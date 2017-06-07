package com.gameit.web;

import com.gameit.config.Authorities;
import com.gameit.model.Authority;
import com.gameit.model.User;
import com.gameit.model.exceptions.EmailExistsException;
import com.gameit.model.exceptions.UsernameExistsException;
import com.gameit.repository.AuthorityRepository;
import com.gameit.service.UserService;
import com.gameit.web.dto.UserWithAuthorities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<?> getUsers() {
        List<User> users = userService.findAll();
        ResponseEntity<?> responseEntity;
        if (users.size() > 0) {
            responseEntity = ResponseEntity.ok(users);
        } else {
            responseEntity = ResponseEntity.noContent().build();
        }
        return responseEntity;
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getOneUser(@PathVariable String id) {
        User user = userService.findById(id);
        ResponseEntity<?> responseEntity;
        if (user != null) {
            responseEntity = ResponseEntity.ok(user);
        } else {
            responseEntity = ResponseEntity.noContent().build();
        }
        return responseEntity;
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteOneUser(@PathVariable String id) {
        userService.delete(id);
        User user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity<?> createNewUser(@RequestBody UserWithAuthorities userObject) {
        ArrayList<Authority> authorities = new ArrayList<>();
        if (!userObject.getAuthorities().isEmpty()) {
            authorities = userObject.getAuthorities()
                    .stream()
                    .map(authority -> authorityRepository.findOne(authority))
                    .collect(Collectors.toCollection(ArrayList<Authority>::new));
        } else {
            authorities.add(authorityRepository.findOne(Authorities.BUYER.name()));
        }

        User user = null;
        try {
            user = userService.create(userObject.getUser(), authorities);
        } catch (EmailExistsException | UsernameExistsException e) {
            return ResponseEntity.badRequest().body(e);
        }
        final URI location = ServletUriComponentsBuilder.
                fromCurrentServletMapping().path("/users/{id}").build()
                .expand(user.getId()).toUri();

        EurekaDiscoveryClient.EurekaServiceInstance mailService = getService("my-mail");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> entity = new HttpEntity<User>(user, headers);
        ResponseEntity<Object> responseEntity = this.restTemplate.postForEntity("http://" + mailService.getInstanceInfo().getIPAddr() + ":8080/mail/register", entity, null);

        return ResponseEntity.created(location).body(user);
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody User userObject) {
        User beforeUpdate = userService.findById(id);
        User user = userService.update(beforeUpdate, userObject);
        return ResponseEntity.ok(user);
    }

    private EurekaDiscoveryClient.EurekaServiceInstance getService(String serviceName) {
        Random rnd = new Random();
        List<ServiceInstance> services = discoveryClient.getInstances(serviceName);
        return (EurekaDiscoveryClient.EurekaServiceInstance) services.get(rnd.nextInt(services.size()));
    }
}
