package com.example.receiver.service;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MessageReceiver {

    @RabbitListener(queues = "${queue.name}")
    public void receiveMessage(String message) {
        System.out.println("Received: " + message);
    }
}

