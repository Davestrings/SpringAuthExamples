package com.example.auth.authentications.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hello")
public class HomeRestController {
    @GetMapping("user")
    public String helloUser(){
        return "Hello User";
    }

    public String helloAdmin(){
        return "Hello Admin";
    }
}
