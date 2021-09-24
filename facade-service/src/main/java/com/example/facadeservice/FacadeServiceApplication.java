package com.example.facadeservice;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class FacadeServiceApplication {

    @Value("${queue.name}")
    private String queueName;
    @Value("${host.name}")
    private String hostname;

    public static void main(String[] args) {
        SpringApplication.run(FacadeServiceApplication.class, args);
    }

    @Bean
    public Queue myQueue() { return new Queue(queueName, true); }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }

    @Bean
    public CachingConnectionFactory connectionFactory() {
        return new CachingConnectionFactory(hostname);
    }

    @Bean
    public RabbitAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

}
