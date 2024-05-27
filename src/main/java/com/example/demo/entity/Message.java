package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Table(name = "message")
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String chatname;
    private String message;



    @ManyToOne
    @JoinColumn(name = "senderId", nullable = false)
    private UserEntity sender;

    private LocalDateTime timestamp; // 시간 생성
// LocalDateTime 클래스의 인스턴스 변수. 날짜와 시간 정보를 표현할 수 있는 클래스
    //timestamp 변수를 사용하여 메시지가 생성된 시간을 기록할 수 있으며, 이 정보를 활용하여 메시지를 정렬하거나 시간대 변환 등의 작업을 수행

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChatname() {
        return chatname;
    }

    public void setChatname(String chatname) {
        this.chatname = chatname;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserEntity getSender() {
        return sender;
    }

    public void setSender(UserEntity sender) {
        this.sender = sender;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
