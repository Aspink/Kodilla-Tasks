package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.config.CompanyDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@Service
public class MailCreatorService {

    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    private CompanyDetails company;

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    public String buildTrelloCardEmail(String message) {
        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "http://localhost:8080");
        context.setVariable("button", "Visit website");
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("preview", "New Trello card created!");
        context.setVariable("company_name", "COMPANY: " +company.getCompanyName());
        context.setVariable("company_contact", "E-mail: " + company.getCompanyEmail() +
                                                        "    Phone: " + company.getCompanyPhone());
        context.setVariable("company_goal", "\"" + company.getCompanyGoal() + "\"");
        return templateEngine.process("mail/created-trello-card-mail", context);
    }
}
