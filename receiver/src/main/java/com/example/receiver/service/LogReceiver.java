package com.example.receiver.service;

import com.example.receiver.model.LogMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class LogReceiver {
    @RabbitListener(queues = "${queue.logs}")
    public void receiveLog(LogMessage logMessage) {
        System.out.println("HERE"+ logMessage);
        System.out.println("HERE"+ logMessage.getMessage());
    }
}
