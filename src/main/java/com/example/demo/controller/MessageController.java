package com.example.demo.controller;

import com.example.demo.entity.Message;
import com.example.demo.service.MessageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/api/message")
    public Message sendMessage(@RequestBody @Valid Message message) {
        String chatname = message.getChatname();
        String messageContent = message.getMessage();
        return messageService.saveMessage(chatname, messageContent);
    }

//    @GetMapping("/api/message")
//    public List<Message> getAllMessages() {
//        return messageService.getAllMessages();
//    }
}
