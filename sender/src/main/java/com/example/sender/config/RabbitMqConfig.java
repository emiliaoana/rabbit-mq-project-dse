package com.example.sender.config;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Bean
    public Queue myQueue(@Value("${queue.name}") String queueName) {
        return new Queue(queueName, false);
    }
}

