package com.group18.getapet.web.controller;

import com.group18.getapet.service.AuthService;
import com.group18.getapet.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class AuthController {
    private final AuthService authService;
    private final UserService userService;
    public AuthController(AuthService authService, UserService userService){
        this.authService = authService;
        this.userService = userService;
    }
}
