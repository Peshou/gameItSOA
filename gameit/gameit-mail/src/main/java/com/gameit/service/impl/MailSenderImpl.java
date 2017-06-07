package com.gameit.service.impl;

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

    @Autowired
    private MailContentBuilder mailContentBuilder;

    @Value("${spring.mail.username}")
    private String username;

    @Async
    public void sendOrderEmail(final UserGameOrder userGameOrder) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper =
                new MimeMessageHelper(
                        mimeMessage, false, CharEncoding.UTF_8);
        messageHelper.setTo(userGameOrder.getUser().getEmail());
        messageHelper.setFrom(username);
        messageHelper.setSubject("Order Confirmation");
        String content = mailContentBuilder.build("newsletterMail", userGameOrder);
        messageHelper.setText(content, true);
//        messageHelper.setText("Dear " + userGameOrder.getUser().getUsername()
//                + ", thank you for placing order. Your order number is "
//                + userGameOrder.getPaymentProcessorChargeId(), true);
        mailSender.send(mimeMessage);

    }
//TODO: FIX THYMELEAF
    @Override
    @Async
    public void sendRegistrationSuccessEmail(User user) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper =
                new MimeMessageHelper(
                        mimeMessage, false, CharEncoding.UTF_8);
        messageHelper.setTo(user.getEmail());
        messageHelper.setFrom(username);
        messageHelper.setSubject("Account Registration");
//        String content = mailContentBuilder.build("registrationMail", user);
//        messageHelper.setText(content, true);
        message.setText("Dear " + user.getUsername()
                + ", Your account has been created. <br/> Thank you for your registration.<br/> The Game It Team.", true);
        mailSender.send(mimeMessage);

    }

    @Override
    @Async
    public void sendNewsLetter(User user) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper message =
                new MimeMessageHelper(
                        mimeMessage, false, CharEncoding.UTF_8);
        message.setTo(user.getEmail());
        message.setFrom(username);
        message.setSubject("Newsletter");
        message.setText("Dear " + user.getUsername()
                + ", Thank you for receiving this newsletter", true);
        mailSender.send(mimeMessage);
    }
}