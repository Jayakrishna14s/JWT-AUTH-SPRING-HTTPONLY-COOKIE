package com.marketanalysis.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserController {
    
    @GetMapping("/user")
    public String check() {
        System.out.println("I am user");
        return "I am user";
    }
}
 