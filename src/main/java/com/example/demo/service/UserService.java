package com.example.demo.service;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserEntity create(UserDto userDto) {
        UserEntity userEntity = UserEntity.builder()
                .username(userDto.getUsername())
                .userpwd(userDto.getUserpwd())
                .build();

        return userRepository.save(userEntity);
    }

    public UserEntity readOne(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));
    }

    public UserEntity createOrLogin(UserDto userDto) {
        // 유저가 존재하는지 확인
        Optional<UserEntity> existingUser = userRepository.findByUsername(userDto.getUsername());
        if (existingUser.isPresent()) {
            // 비밀번호가 일치하는지 확인
            if (existingUser.get().getUserpwd().equals(userDto.getUserpwd())) {
                return existingUser.get();
            } else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호 오류");
            }
        } else {
            // 새로운 사용자 등록
            return create(userDto);
        }
    }
}

