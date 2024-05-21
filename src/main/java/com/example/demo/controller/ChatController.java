package com.example.demo.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.CrossOrigin;
@Controller
@Log4j2
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class ChatController {

    @GetMapping("/chat")
    public String chatGet() {
        log.info("@ChatController, chat GET()");
        return "chater"; // chater.html 반환
    }
//POST 요청을 보내면 서버가 이를 처리할 수 있게
    @PostMapping("/chat")
    @ResponseBody
    public String chatPost(@RequestBody String message) {
        log.info("@ChatController, chat POST() - Received message: " + message);
        return "Message received: " + message;
    }
}