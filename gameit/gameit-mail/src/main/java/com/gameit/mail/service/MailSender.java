package com.gameit.mail.service;


import com.gameit.mail.model.User;
import com.gameit.mail.model.UserGameOrder;

import javax.mail.MessagingException;

public interface MailSender {

    void sendRegistrationSuccessEmail(User user) throws MessagingException;
    void sendNewsLetter(User user) throws MessagingException;
    void sendOrderEmail(UserGameOrder userGameOrder) throws MessagingException;
}