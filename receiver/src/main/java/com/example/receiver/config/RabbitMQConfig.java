package com.example.receiver.config;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${queue.name}")
    private String queueName;

    @Value("${queue.logs}")
    private String logsQueueName;

    @Bean
    public Queue myQueue() {
        return new Queue(queueName, false);
    }

    @Bean
    public Queue logsQueue() {
        return new Queue(logsQueueName, false);
    }

    @Bean
    public TopicExchange logsExchange() {
        return new TopicExchange("logs.exchange");
    }

    @Bean
    public Binding logsBinding(Queue logsQueue, TopicExchange exchange) {
        return BindingBuilder
                .bind(logsQueue)
                .to(exchange)
                .with("logs.#");
    }
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}

