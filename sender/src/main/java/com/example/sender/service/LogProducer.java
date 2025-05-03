package com.example.sender.service;

import com.example.sender.model.LogMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Random;

@Service
public class LogProducer {

    private final RabbitTemplate rabbitTemplate;
    @Value("${queue.logs}")
    private  String queueName;
    @Autowired
    public LogProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    private final String[] levels = {"INFO", "DEBUG", "ERROR", "WARN"};
    private final String[] services = {"auth-service", "payment-service", "order-service"};

    private final Random random = new Random();

    @Scheduled(cron = "0 * * * * ?")
    public void sendLogMessage() {
        LogMessage log = new LogMessage();
        log.setService(getRandomService());
        log.setLevel(getRandomLevel());
        log.setMessage("Simulated log message from " + log.getService());
        log.setTimestamp(Instant.now().toString());

        rabbitTemplate.convertAndSend(queueName, log);

        System.out.println("Sent: " + log);
    }

    private String getRandomLevel() {
        return levels[random.nextInt(levels.length)];
    }

    private String getRandomService() {
        return services[random.nextInt(services.length)];
    }
}

