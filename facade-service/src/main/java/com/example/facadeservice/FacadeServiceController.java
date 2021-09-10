package com.example.facadeservice;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/facade-service")
public class FacadeServiceController {

    private final WebClient loggingService = WebClient.create("http://localhost:8082/logging-service");
    private final WebClient messageService = WebClient.create("http://localhost:8083/messages-service");

    private void log(String log) {
        System.out.println(log);
    }

    @GetMapping
    public Mono<String> getMessages() {
        log("Client sent a get request");

        Mono<String> loggingServiceResponse = loggingService
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

    @PostMapping
    public Mono<Void> postMessage(@RequestBody String text) {
        log("Client sent a post request with message: " + text);

        return loggingService
                .post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new Message(UUID.randomUUID(), text))
                .retrieve()
                .bodyToMono(Void.class);
    }
}
