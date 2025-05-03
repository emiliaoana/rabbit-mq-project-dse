package com.example.receiver.service;

import com.example.receiver.model.Email;
import com.example.receiver.model.LogMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class LogReceiver {
    Logger logger = Logger.getLogger(LogReceiver.class.getName());
    private final EmailService emailService;

    public LogReceiver(EmailService emailService) {
        this.emailService = emailService;
    }
    @RabbitListener(queues = "${queue.logs}")
    public void receiveLog(LogMessage logMessage) {
        if(logMessage.getLevel().equals("ERROR")) {
            logger.severe("Error message received: " + logMessage.getMessage() + " sending email");
            Email email = new Email("emiliaoana01@gmail.com", "Error message", logMessage.getMessage());
            emailService.sendMail(email);
            logger.info("Email sent to: " + email.getTo() + " with subject: " + email.getSubject());
        } else {
            logger.info("Message received: " + logMessage.getMessage());
        }
    }
}
