package com.example.sender.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MessageSender {

    private final RabbitTemplate rabbitTemplate;
    private final String queueName;

    public MessageSender(RabbitTemplate rabbitTemplate, @Value("${queue.name}") String queueName) {
        this.rabbitTemplate = rabbitTemplate;
        this.queueName = queueName;
    }

    public void sendMessage(String message) {
        rabbitTemplate.convertAndSend(queueName, message);
        System.out.println("Sent: " + message);
    }
}
