package com.example.sender.controller;
import com.example.sender.service.MessageSender;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageSender messageSender;

    @Autowired
    public MessageController(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @PostMapping("/send")
    public String sendMessage(@RequestParam String message) {
        messageSender.sendMessage(message);
        return "Message sent: " + message;
    }
}

