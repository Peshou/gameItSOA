package com.gameit.service.impl;

import com.gameit.model.User;
import com.gameit.model.UserGameOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailContentBuilder {

    private TemplateEngine templateEngine;

    @Autowired
    public MailContentBuilder(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String buildNewsletterTemplate(String mailName, User user) {
        Context context = new Context();
        context.setVariable("user", user);
        return templateEngine.process(mailName, context);
    }

    public String buildOrderTemplate(String mailName, UserGameOrder userGameOrder) {
        Context context = new Context();
        context.setVariable("user", userGameOrder.getUser());
        context.setVariable("orderNumber", userGameOrder.getPaymentProcessorChargeId());
        return templateEngine.process(mailName, context);
    }

    public String buildRegisterTemplate(String mailName, User user) {
        Context context = new Context();
        context.setVariable("user", user);
        return templateEngine.process(mailName, context);
    }
}
