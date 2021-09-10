package com.example.messagesservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/messages-service")
public class MessagesServiceController {

    private void log(String log) {
        System.out.println(log);
    }

    @GetMapping
    public String getMessages() {
        log("Facade service sent a get request");
        return "Message Service is not implemented yet";
    }
}
