package com.example.demo.result;
import com.example.demo.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
//import your.project.dto.UserResult;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResult {

    private Long id;
    private String username;
    private String userpwd;

    public UserResult(UserEntity users) {
        this.id = users.getId();
        this.username = users.getUsername();
        this.userpwd = users.getUserpwd();
    }
}
