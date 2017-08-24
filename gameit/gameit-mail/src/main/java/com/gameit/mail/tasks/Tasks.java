package com.gameit.mail.tasks;

import com.gameit.mail.model.User;
import com.gameit.mail.service.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Random;

@Component
public class Tasks {
    @Autowired
    private MailSender mailSender;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    RestTemplate restTemplate;

    //EVERY DAY FROM 10 - 18, EVERY HALF AN HOUR, 10:30, 11:00, 11:30, 12:00, 12:30 ....
    @Scheduled(cron = "* * 0/30 * * *")
    public void sendPromotionEmail() {
        EurekaDiscoveryClient.EurekaServiceInstance authService = getService("my-auth");


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> entity = new HttpEntity<Object>(null, headers);

        ResponseEntity<User[]> responseEntity =
                this.restTemplate.exchange("http://" + authService.getInstanceInfo().getIPAddr() + ":8080/users", HttpMethod.GET, entity, User[].class);


        for (User user : responseEntity.getBody()) {
            try {
                mailSender.sendNewsLetter(user);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }

    private EurekaDiscoveryClient.EurekaServiceInstance getService(String serviceName) {
        Random rnd = new Random();
        List<ServiceInstance> services = discoveryClient.getInstances(serviceName);
        return (EurekaDiscoveryClient.EurekaServiceInstance) services.get(rnd.nextInt(services.size()));
    }
}
