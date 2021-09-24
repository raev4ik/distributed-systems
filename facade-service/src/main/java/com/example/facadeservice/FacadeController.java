package com.example.facadeservice;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
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
