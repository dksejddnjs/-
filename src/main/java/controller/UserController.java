package controller;

import dto.UserDto;
import entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import result.UserResult;
import service.UserService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResult> create(@RequestBody UserDto userDto)  {
        log.info("create Users");
        UserEntity users = userService.create(userDto);
        UserResult userResult = new UserResult(users);
        return ResponseEntity.ok(userResult);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserResult> getUser(@PathVariable Long id)  {
        log.info("readOne");
        UserEntity users = userService.readOne(id);
        UserResult userResult = new UserResult(users);
        return ResponseEntity.ok(userResult);
    }

}
