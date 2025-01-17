package com.evaluacionFinal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.evaluacionFinal.model.User;
import com.evaluacionFinal.service.UserService;

@RestController
@RequestMapping("/api/usuarios")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public User signup(@RequestBody User user) {
        return userService.signup(user);
    }

    @PostMapping("/signin")
    public String signin(@RequestBody User user) {
        return userService.signin(user.getUsername(), user.getPassword());
    }
}
