package com.example.demo.controller;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.UserEntity;
import com.example.demo.result.UserResult;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResult> create(@RequestBody UserDto userDto) {
        log.info("create Users");
        UserEntity users = userService.create(userDto);
        UserResult userResult = new UserResult(users);
        return ResponseEntity.ok(userResult);
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
