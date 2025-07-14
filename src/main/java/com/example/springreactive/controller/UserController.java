package com.example.springreactive.controller;

import com.example.springreactive.model.User;
import com.example.springreactive.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users/age-above-fifty")
    public Flux<User> getUsersAboveFifty() {
        return userService.getUsersAboveFifty();
    }
}