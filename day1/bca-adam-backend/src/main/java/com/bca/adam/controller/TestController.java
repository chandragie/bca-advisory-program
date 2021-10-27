package com.bca.adam.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hi")
public class TestController {
    
    @GetMapping("")
    public String hi(){
        return "Welcome to BCA Advisory Program (ADAM) 👋🏻";
    }
}
