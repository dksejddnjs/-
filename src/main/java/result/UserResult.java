package result;
import entity.UserEntity;
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
    private String usermsg;

    public UserResult(UserEntity users) {
        this.id = users.getId();
        this.username = users.getUsername();
        this.usermsg = users.getUsermsg();
    }
}
