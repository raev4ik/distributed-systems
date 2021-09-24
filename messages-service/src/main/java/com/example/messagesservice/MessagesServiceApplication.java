package com.example.messagesservice;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class MessagesServiceApplication {

    @Value("${queue.name}")
    private String queueName;

    public static void main(String[] args) {
        SpringApplication.run(MessagesServiceApplication.class, args);
    }

    @Bean
    public Queue myQueue() {
        return new Queue(queueName, true);
    }

}
