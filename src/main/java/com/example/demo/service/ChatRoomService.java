//package com.example.demo.service;
//
//import com.example.demo.entity.ChatRoom;
//import com.example.demo.repository.ChatRoomRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class ChatRoomService {
//
//    private final ChatRoomRepository chatRoomRepository;
//
//    @Autowired
//    public ChatRoomService(ChatRoomRepository chatRoomRepository) {
//        this.chatRoomRepository = chatRoomRepository;
//    }
//
//    // 모든 채팅룸 가져오기
//    public List<ChatRoom> getAllChatRooms() {
//        return chatRoomRepository.findAll();
//    }
//
//    // 특정 이름의 채팅룸 가져오기
////    public ChatRoom getChatRoomByName(String name) {
////        return chatRoomRepository.findByName(name)
////                .orElseThrow(() -> new RuntimeException("채팅룸을 찾을 수 없습니다: " + name));
////    }
//
//    // 채팅룸 저장하기
//    public ChatRoom saveChatRoom(ChatRoom chatRoom) {
//        return chatRoomRepository.save(chatRoom);
//    }
//
//    // 채팅룸 삭제하기
//    public void deleteChatRoom(ChatRoom chatRoom) {
//        chatRoomRepository.delete(chatRoom);
//    }
//}