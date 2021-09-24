package com.example.messagesservice;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class MessagesService {
    private List<String> messagesList = new ArrayList<>();

    @Value("${queue.name}")
    private String queueName;

    @RabbitListener(queues = ("${queue.name}"))
    public void listen(String message) {
        System.out.println("Message-service received message" + message);
        messagesList.add(message);
    }

    public String getMessages() {
        String messages = messagesList.toString();
        System.out.println("Message-service getMessages()" + messages);
        return messages;
    }
}
