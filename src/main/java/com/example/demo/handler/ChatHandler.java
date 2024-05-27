package com.example.demo.handler;

import com.example.demo.service.MessageService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashSet;
import java.util.Set;

@Component
@Log4j2
public class ChatHandler extends TextWebSocketHandler {

    private final Set<WebSocketSession> sessions = new HashSet<>();
    private final MessageService messageService;

    public ChatHandler(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload: {}", payload);

        // 메시지의 형식이 username:message일 때 처리
        if (payload.contains(":")) {
            String[] parts = payload.split(":", 2);
            String username = parts[0].trim();
            String messageContent = parts[1].trim();
            session.getAttributes().put("username", username);  // username을 세션 속성에 저장
            log.info("Username: {}", username);

            // 메시지 저장
            messageService.saveMessage(username, messageContent);

            // 모든 세션에 메시지 전송
            for (WebSocketSession sess : sessions) {
                if (sess.isOpen()) {
                    sess.sendMessage(new TextMessage(username + ": " + messageContent));
                }
            }
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        log.info(session + " 클라이언트 접속");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        log.info(session + " 클라이언트 접속 해제");
    }
}
