package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.config.CompanyDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;


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
        List<String> functionality = new ArrayList<>();
        functionality.add("You can manage your tasks");
        functionality.add("Provides connection with Trello Account");
        functionality.add("Application allows sending tasks to Trello");

        List<String> cheklist = new ArrayList<>();
        cheklist.add("Do your tasks!");
        cheklist.add("Do not waste your pace!");
        cheklist.add("Do not forget to rest(Api ;D)!");

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "http://localhost:8080");
        context.setVariable("trello_url", "https://trello.com/b/kZ4y6XON/kodilla-application");
        context.setVariable("button", "Visit website");
        context.setVariable("button_daily", "Visit your Trello!");
        context.setVariable("show_Button", false);
        context.setVariable("show_Button_daily", true);
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("admin_config", adminConfig);
        context.setVariable("is_friend", true);
        context.setVariable("application_functionality", functionality);
        context.setVariable("cheklist", cheklist);
        context.setVariable("preview", "New Trello card created!");
        context.setVariable("preview2", "Daily email");
        context.setVariable("company_name", "COMPANY: " +company.getCompanyName());
        context.setVariable("company_contact", "E-mail: " + company.getCompanyEmail() +
                                                        "    Phone: " + company.getCompanyPhone());
        context.setVariable("company_goal", "\"" + company.getCompanyGoal() + "\"");
        if (message.contains("New card:")) return templateEngine.process("mail/created-trello-card-mail", context);
        return templateEngine.process("mail/once-a-day-email", context);
    }
}
