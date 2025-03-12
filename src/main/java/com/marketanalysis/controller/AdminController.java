package com.marketanalysis.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AdminController {
    
    @GetMapping("/admin/check")
    public String check() {
        System.out.println("I am admin");
        return "I am admin";
    }
}
 