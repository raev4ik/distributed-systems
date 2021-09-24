package com.example.consul;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomController {
    @GetMapping("/hello")
    public String get() {
        return "Hello there!";
    }
}
