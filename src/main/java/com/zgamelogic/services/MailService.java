package com.zgamelogic.services;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private static JavaMailSender emailSender;

    @Autowired
    private JavaMailSender preConstructEmailSender;

    @PostConstruct
    private void initialize() {
        emailSender = this.preConstructEmailSender;
    }

    public static void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("website@zgamelogic.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        emailSender.send(message);
    }
}
