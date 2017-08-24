package com.gameit.mail.service.impl;

import com.gameit.mail.model.User;
import com.gameit.mail.model.UserGameOrder;
import com.gameit.mail.service.MailSender;
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
    @Override
    public void sendOrderEmail(UserGameOrder userGameOrder) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, false, CharEncoding.UTF_8);
        messageHelper.setTo(userGameOrder.getUser().getEmail());
        messageHelper.setFrom(username);
        messageHelper.setSubject("Order Confirmation");

        String content = mailContentBuilder.buildOrderTemplate("orderMail", userGameOrder);
        messageHelper.setText(content, true);

        mailSender.send(mimeMessage);

    }

    @Override
    @Async
    public void sendRegistrationSuccessEmail(User user) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, false, CharEncoding.UTF_8);
        messageHelper.setTo(user.getEmail());
        messageHelper.setFrom(username);
        messageHelper.setSubject("Account Registration");

        String content = mailContentBuilder.buildRegisterTemplate("registrationMail", user);
        messageHelper.setText(content, true);
        mailSender.send(mimeMessage);

    }

    @Override
    @Async
    public void sendNewsLetter(User user) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper =
                new MimeMessageHelper(
                        mimeMessage, false, CharEncoding.UTF_8);
        messageHelper.setTo(user.getEmail());
        messageHelper.setFrom(username);
        messageHelper.setSubject("Newsletter");

        String content = mailContentBuilder.buildNewsletterTemplate("newsletterMail", user);
        messageHelper.setText(content, true);

        mailSender.send(mimeMessage);
    }
}