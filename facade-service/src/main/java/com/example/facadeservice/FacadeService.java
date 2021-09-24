package com.example.facadeservice;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Component
public class FacadeService {

    private final RabbitTemplate rabbitTemplate;
    private final DiscoveryClient discoveryClient;

    private List<ServiceInstance> loggingServices;
    private List<ServiceInstance> messagesServices;

    @Value("${logging.service.name}")
    private String loggingServiceName;
    @Value("${messages.service.name}")
    private String messagesServiceName;
    @Value("${queue.name}")
    private String queueName;

    @Autowired
    public FacadeService(RabbitTemplate rabbitTemplate, DiscoveryClient discoveryClient) {
        this.rabbitTemplate = rabbitTemplate;
        this.discoveryClient = discoveryClient;
    }

    private void log(String log) {
        System.out.println(log);
    }

    public Mono<Void> addMessage(String text) {
        loggingServices = discoveryClient.getInstances(loggingServiceName);
        rabbitTemplate.convertAndSend(queueName,text);
        return WebClient.create(loggingServices.get(new Random().nextInt(loggingServices.size())).getUri().toString())
                .post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new Message(UUID.randomUUID(), text))
                .retrieve()
                .bodyToMono(Void.class);
    }

    public Mono<String> getMessages() {
        log("Facade Service get messages");
        loggingServices = discoveryClient.getInstances(loggingServiceName);
        messagesServices = discoveryClient.getInstances(messagesServiceName);
        Mono<String> loggingServiceResponse =
                WebClient.create(discoveryClient.getInstances(loggingServiceName).get(new Random().nextInt(loggingServices.size())).getUri().toString())
                .get()
                .retrieve()
                .bodyToMono(String.class);

        Mono<String> messageServiceResponse =
                WebClient.create(discoveryClient.getInstances(messagesServiceName).get(new Random().nextInt(messagesServices.size())).getUri().toString())
                .get()
                .retrieve()
                .bodyToMono(String.class);

        return loggingServiceResponse.zipWith(messageServiceResponse,
                ((logResp, messResp) -> "Logging Service Response: " + logResp + "\n" + "Message Service Response: " + messResp)).onErrorReturn("Error!!!!!");
    }
}
