package com.coe.chat.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.example.demo.Greeting;
import com.example.demo.HelloMessage;

@Controller
public class GreetingController {
    @MessageMapping("/hello")
    @SendTo("/client/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Greeting("Hello, " + message.getName() + "!");
    }

}
