package com.gameit.service;


import com.gameit.model.Game;
import com.gameit.model.User;
import com.gameit.model.UserGameOrder;

import javax.mail.MessagingException;

public interface MailSender {

    void sendReistrationSuccessEmail(User user) throws MessagingException;
    void sendNewsLetter(User user) throws MessagingException;
    void sendOrderEmail(UserGameOrder userGameOrder) throws MessagingException;
}