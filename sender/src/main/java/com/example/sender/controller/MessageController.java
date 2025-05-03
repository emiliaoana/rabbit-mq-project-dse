package com.example.sender.controller;
import com.example.sender.service.LogProducer;
import com.example.sender.service.MessageSender;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageSender messageSender;
    private final LogProducer logProducer;

    @Autowired
    public MessageController(MessageSender messageSender, LogProducer logProducer) {
        this.messageSender = messageSender;
        this.logProducer = logProducer;
    }

    @PostMapping("/send")
    public String sendMessage(@RequestParam String message) {
        messageSender.sendMessage(message);
        return "Message sent: " + message;
    }

    @PostMapping("/send-logs")
    public void sendLogs() {
        logProducer.sendLogMessage();
    }
}

