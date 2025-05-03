package com.example.receiver.service;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import com.example.receiver.model.LogMessage;

@Service
public class MessageReceiver {

    @RabbitListener(queues = "${queue.name}")
    public void receiveMessage(LogMessage message) {
        System.out.println("Received message from service: " + message.getService());
    }
}

