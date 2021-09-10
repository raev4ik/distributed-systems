package com.example.facadeservice;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Component
public class FacadeService {

    private final List<WebClient> loggingServices = List.of(
            WebClient.create("http://localhost:8082/logging-service"),
            WebClient.create("http://localhost:8083/logging-service"),
            WebClient.create("http://localhost:8084/logging-service"));
    private final WebClient messageService = WebClient.create("http://localhost:8086/messages-service");

    private void log(String log) {
        System.out.println(log);
    }

    public Mono<Void> addMessage(String text) {
        log("Facade Service add message: " + text);

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

        Mono<String> messageServiceResponse = messageService
                .get()
                .retrieve()
                .bodyToMono(String.class);

        return loggingServiceResponse.zipWith(messageServiceResponse,
                ((logResp, messResp) -> "Logging Service Response: " + logResp + "\n" + "Message Service Response: " + messResp)).onErrorReturn("Error!!!!!");
    }
}
