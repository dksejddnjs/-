package com.example.demo.service;

import com.example.demo.entity.Message;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.MessageRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    public Message saveMessage(String username, String messageContent) {
        UserEntity sender = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없음"));

        // 만약 messageContent에 님이 입장하셨습니다.와 같은 형태라면 저장하지 않음
        if (!messageContent.endsWith("님이 입장하셨습니다.")) {
            Message message = new Message();
            message.setChatname(username);
            message.setMessage(messageContent);
            message.setSender(sender);
            message.setTimestamp(LocalDateTime.now());

            return messageRepository.save(message);
        }

        // 입장 메시지는 저장하지 않고 null 반환
        return null;
    }


    public List<Message> getChatHistory(String username) {
        // 사용자의 채팅 기록을 DB에서 조회
        return messageRepository.findByChatname(username);
    }
}
