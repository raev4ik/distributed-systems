package com.example.facadeservice;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Component
public class FacadeService {

    private final String QUEUE = "lab6";

    private final List<WebClient> loggingServices = List.of(
            WebClient.create("http://localhost:8082/logging-service"),
            WebClient.create("http://localhost:8083/logging-service"),
            WebClient.create("http://localhost:8084/logging-service"));
    private final List<WebClient> messagesServices = List.of(
            WebClient.create("http://localhost:8086/messages-service"),
            WebClient.create("http://localhost:8087/messages-service"));

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public FacadeService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    private void log(String log) {
        System.out.println(log);
    }

    public Mono<Void> addMessage(String text) {
        rabbitTemplate.convertAndSend("lab6", text);
        return loggingServices.get(new Random().nextInt(loggingServices.size()))
                .post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new Message(UUID.randomUUID(), text))
                .retrieve()
                .bodyToMono(Void.class);
    }

    public Mono<String> getMessages() {
        log("Facade Service get messages");

        Mono<String> loggingServiceResponse = loggingServices.get(new Random().nextInt(loggingServices.size()))
                .get()
                .retrieve()
                .bodyToMono(String.class);

        Mono<String> messageServiceResponse = messagesServices.get(new Random().nextInt(messagesServices.size()))
                .get()
                .retrieve()
                .bodyToMono(String.class);

        return loggingServiceResponse.zipWith(messageServiceResponse,
                ((logResp, messResp) -> "Logging Service Response: " + logResp + "\n" + "Message Service Response: " + messResp)).onErrorReturn("Error!!!!!");
    }
}
