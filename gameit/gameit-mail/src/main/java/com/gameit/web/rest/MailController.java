package com.gameit.web.rest;

import com.gameit.model.User;
import com.gameit.model.UserGameOrder;
import com.gameit.service.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
public class MailController {
    @Autowired
    private MailSender mailSender;

    @PostMapping("/mail/order")
    public ResponseEntity sendOrderEmail(@RequestBody UserGameOrder userGameOrder) {
        try {
            this.mailSender.sendOrderEmail(userGameOrder);
            return ResponseEntity.ok().build();
        } catch (MessagingException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/mail/register")
    public ResponseEntity sendRegistration(@RequestBody User user) {
        try {
            this.mailSender.sendRegistrationSuccessEmail(user);
            return ResponseEntity.ok().build();
        } catch (MessagingException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
