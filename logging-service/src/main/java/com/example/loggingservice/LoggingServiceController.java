package com.example.loggingservice;

import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/logging-service")
public class LoggingServiceController {

    private final Map<UUID, String> messages = new ConcurrentHashMap<>();

    private void log(String log) {
        System.out.println(log);
    }

    @PostMapping
    public void postLoggedMessage(@RequestBody Message message) {
        log("Facade service sent a post request with message object: " + message);
        messages.put(message.getUuid(), message.getText());
    }

    @GetMapping
    public String getLoggedMessages() {
        log("Facade service sent a get request");
        return messages.values().toString();
    }
}
