package com.example.demo.controller;

import com.example.demo.entity.Message;
import com.example.demo.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ChatHistoryController {

    private final MessageService messageService;

    @Autowired
    public ChatHistoryController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/api/chat/history/{username}")
    public List<Message> getChatHistory(@PathVariable String username) {
        // 사용자의 채팅 기록을 DB에서 조회
        return messageService.getChatHistory(username);
    }


}
