package com.example.facadeservice;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/facade-service")
public class FacadeController {

    private final FacadeService facadeService;
    public FacadeController(FacadeService facadeService) {
        this.facadeService = facadeService;
    }
    private void log(String log) {
        System.out.println(log);
    }

    @GetMapping
    public Mono<String> getMessages() {
        log("FacadeController get request");
        return facadeService.getMessages();
    }

    @PostMapping
    public Mono<Void> postMessage(@RequestBody String text) {
        log("FacadeController post request");
        return facadeService.addMessage(text);
    }
}
