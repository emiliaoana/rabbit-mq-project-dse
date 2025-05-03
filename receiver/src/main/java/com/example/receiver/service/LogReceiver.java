package com.example.receiver.service;

import com.example.receiver.model.Email;
import com.example.receiver.model.LogMessage;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class LogReceiver {
    Logger logger = Logger.getLogger(LogReceiver.class.getName());
    private final EmailService emailService;
    private final MeterRegistry meterRegistry;
    private final AtomicLong messageCounter;
    private final AtomicLong errorCounter;
    private final Timer processingTimer;

    public LogReceiver(EmailService emailService, MeterRegistry meterRegistry) {
        this.emailService = emailService;
        this.meterRegistry = meterRegistry;
        this.messageCounter = meterRegistry.gauge("rabbitmq.messages.received", new AtomicLong(0));
        this.errorCounter = meterRegistry.gauge("rabbitmq.messages.errors", new AtomicLong(0));
        this.processingTimer = Timer.builder("rabbitmq.message.processing.time")
                .description("Time taken to process messages")
                .register(meterRegistry);
    }

    @RabbitListener(queues = "${queue.logs}")
    public void receiveLog(LogMessage logMessage) {
        Instant start = Instant.now();
        messageCounter.incrementAndGet();

        try {
            if(logMessage.getLevel().equals("ERROR")) {
                errorCounter.incrementAndGet();
                logger.severe("Error message received: " + logMessage.getMessage() + " sending email");
                Email email = new Email("emiliaoana01@gmail.com", "Error message", logMessage.getMessage());
                emailService.sendMail(email);
                logger.info("Email sent to: " + email.getTo() + " with subject: " + email.getSubject());
            } else {
                logger.info("Message received: " + logMessage.getMessage());
            }

            // Record message processing time
            Duration processingDuration = Duration.between(start, Instant.now());
            processingTimer.record(processingDuration);

            // Record message latency if timestamp is available
            if (logMessage.getTimestamp() != null) {
                try {
                    Instant messageTime = Instant.parse(logMessage.getTimestamp());
                    Duration latency = Duration.between(messageTime, Instant.now());
                    meterRegistry.timer("rabbitmq.message.latency").record(latency);
                } catch (Exception e) {
                    logger.warning("Could not parse message timestamp: " + logMessage.getTimestamp());
                }
            }
        } catch (Exception e) {
            meterRegistry.counter("rabbitmq.processing.errors").increment();
            throw e;
        }
    }
}
