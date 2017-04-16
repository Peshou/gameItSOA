package com.gameit.service.impl;

import com.gameit.model.Game;
import com.gameit.model.User;
import com.gameit.model.UserGameOrder;
import com.gameit.service.MailSender;
import org.apache.commons.codec.CharEncoding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailSenderImpl implements MailSender {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String username;

    @Async
    public void placeOrder(final UserGameOrder userGameOrder) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper message =
                    new MimeMessageHelper(
                            mimeMessage, false, CharEncoding.UTF_8);
            message.setTo(userGameOrder.getUser().getEmail());
            message.setFrom(username);
            message.setSubject("Order Confirmation");
            message.setText("Dear " + userGameOrder.getUser().getUsername()
                    + ", thank you for placing order. Your order number is "
                    + userGameOrder.getPaymentProcessorChargeId(), true);
            mailSender.send(mimeMessage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @Async
    public void sendReistrationSuccessEmail(User user) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper message =
                    new MimeMessageHelper(
                            mimeMessage, false, CharEncoding.UTF_8);
            message.setTo(user.getEmail());
            message.setFrom(username);
            message.setSubject("Account Registration");
            message.setText("Dear " + user.getUsername()
                    + ", Your account has been created. <br/> Thank you for your registration.<br/> The Game It Team.", true);
            mailSender.send(mimeMessage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @Async
    public void sendNewsLetter(User user) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper message =
                    new MimeMessageHelper(
                            mimeMessage, false, CharEncoding.UTF_8);
            message.setTo(user.getEmail());
            message.setFrom(username);
            message.setSubject("Newsletter");
            message.setText("Dear " + user.getUsername()
                    + ", Thank you for receiving this newsletter", true);
            mailSender.send(mimeMessage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Async
    @Override
    public void sendOrderEmail(User user, Game game, String orderId) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper message =
                    new MimeMessageHelper(
                            mimeMessage, false, CharEncoding.UTF_8);
            message.setTo(user.getEmail());
            message.setFrom(username);
            message.setSubject("Order Confirmation");
            message.setText("Dear " + user.getUsername()
                    + ", thank you for placing order. <br/> Your order number is "+ orderId, true);
            mailSender.send(mimeMessage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}