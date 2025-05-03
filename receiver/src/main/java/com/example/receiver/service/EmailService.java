package com.example.receiver.service;

import com.example.receiver.model.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
@Service
public class EmailService {

    @Value("misemilia01@gmail.com")
    private String fromEmail;

    private final JavaMailSender emailSender;

    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }


    public void sendMail(Email email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(email.getTo());
        message.setSubject(email.getSubject());
        message.setText(email.getBody());
        emailSender.send(message);
    }
}