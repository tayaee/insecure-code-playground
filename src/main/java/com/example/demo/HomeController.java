package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
class HomeController {
    @GetMapping
    public String homeEndpoint() {
        return "<li><a href='/sqli'>SQL Injection demo</a>" +
                "<li><a href='/cmdi'>CMD Injection demo</a>" +
                "<li><a href='/pt'>Path Traversal demo</a>";
    }
}
