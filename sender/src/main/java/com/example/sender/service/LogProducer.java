package com.example.sender.service;

import com.example.sender.model.LogMessage;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class LogProducer {

    private final RabbitTemplate rabbitTemplate;
    private final MeterRegistry meterRegistry;
    private final AtomicLong messageCounter;
    @Value("${queue.logs}")
    private  String queueName;
    @Autowired
    public LogProducer(RabbitTemplate rabbitTemplate, MeterRegistry meterRegistry) {
        this.rabbitTemplate = rabbitTemplate;
        this.meterRegistry = meterRegistry;
        this.messageCounter = meterRegistry.gauge("rabbitmq.messages.sent", new AtomicLong(0));
    }

    private final String[] levels = {"INFO", "DEBUG", "ERROR", "WARN"};
    private final String[] services = {"auth-service", "payment-service", "order-service"};

    private final Random random = new Random();

//    @Scheduled(cron = "0/30  * * * * ?")
    @Scheduled(fixedRate = 1)
    public void sendLogMessage() {
        LogMessage log = new LogMessage();
        log.setService(getRandomService());
        log.setLevel("ERROR");
        log.setMessage("Simulated log message from " + log.getService());
        log.setTimestamp(Instant.now().toString());

        rabbitTemplate.convertAndSend(queueName, log);
        rabbitTemplate.convertAndSend(queueName, log);
        rabbitTemplate.convertAndSend(queueName, log);
        rabbitTemplate.convertAndSend(queueName, log);
        messageCounter.incrementAndGet();
        System.out.println("Sent: " + log);
    }

    private String getRandomLevel() {
        return levels[random.nextInt(levels.length)];
    }

    private String getRandomService() {
        return services[random.nextInt(services.length)];
    }
}

