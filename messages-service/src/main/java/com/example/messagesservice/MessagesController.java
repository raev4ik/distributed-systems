package com.example.messagesservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessagesController {

    private MessagesService messagesService;
    public MessagesController(MessagesService messagesService) {
        this.messagesService = messagesService;
    }
    private void log(String log) {
        System.out.println(log);
    }

    @GetMapping
    public String getMessages() {
        log("Facade service sent a get request");
        return messagesService.getMessages();
    }
}
