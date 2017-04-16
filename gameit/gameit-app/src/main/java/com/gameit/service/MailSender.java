package com.gameit.service;


import com.gameit.model.Game;
import com.gameit.model.User;
import com.gameit.model.UserGameOrder;

import javax.mail.MessagingException;

public interface MailSender {

    void placeOrder(UserGameOrder userGameOrder);
    void sendReistrationSuccessEmail(User user);
    void sendNewsLetter(User user);
    void sendOrderEmail(User user, Game game, String orderId) throws MessagingException;
}