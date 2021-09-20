package com.example.messagesservice;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class MessagesService {
    private List<String> messagesList = new ArrayList<>();
    private final String QUEUE_NAME = "lab6";

    public MessagesService() {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.basicQos(1);
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                messagesList.add(message);
                System.out.println("Message-service received message" + message);
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), true);
            };
            channel.basicConsume(QUEUE_NAME, false, deliverCallback, consumerTag -> {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getMessages() {
        String messages = messagesList.toString();
        System.out.println("Message-service getMessages()" + messages);
        return messages;
    }
}
