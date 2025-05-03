package com.example.receiver.controller;

import com.example.receiver.model.Email;
import com.example.receiver.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
public class EmailController {
    @Autowired
    private EmailService emailService;

    @PostMapping("/send")
    public void sendMail(@RequestBody Email email) {
        emailService.sendMail(email);
    }

}