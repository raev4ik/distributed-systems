package com.example.loggingservice;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/logging-service")
public class LoggingController {

    private final LoggingService loggingService;

    public LoggingController(LoggingService loggingService) {
        this.loggingService = loggingService;
    }

    private void log(String log) {
        System.out.println(log);
    }

    @PostMapping
    public void postLoggedMessage(@RequestBody Message message) {
        log("LoggingController post message: " + message);
        loggingService.addMessage(message);
    }

    @GetMapping
    public String getLoggedMessages() {
        log("LoggingController get messages");
        return loggingService.getMessages();
    }
}
