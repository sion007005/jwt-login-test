package com.sion.jwtlogintest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("home")
    public String home() {
        return "hello, this is our main entrance.";
    }

    @PostMapping("token")
    public String token() {
        return "<h1>token</h1>";
    }
}
