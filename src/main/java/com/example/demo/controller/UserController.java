package com.example.demo.controller;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.UserEntity;
import com.example.demo.result.UserResult;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
        import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> createOrLogin(@RequestBody UserDto userDto) {
        log.info("createOrLogin User: {}", userDto);
        try {
            UserEntity user = userService.createOrLogin(userDto);
            UserResult userResult = new UserResult(user);
            return ResponseEntity.ok(userResult);
        } catch (ResponseStatusException e) {
            log.error("사용자 등록 또는 로그인 중 오류 발생: {}", e.getReason(), e);
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        } catch (Exception e) {
            log.error("오류 발생: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("오류 발생");
        }
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello!";
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResult> getUser(@PathVariable Long id) {
        log.info("readOne");
        UserEntity users = userService.readOne(id);
        UserResult userResult = new UserResult(users);
        return ResponseEntity.ok(userResult);
    }
}
