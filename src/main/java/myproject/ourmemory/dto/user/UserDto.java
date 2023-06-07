package myproject.ourmemory.dto.user;

import lombok.Getter;
import myproject.ourmemory.domain.User;
import myproject.ourmemory.domain.UserRole;

@Getter
public class UserDto {

    private Long id;
    private String name;
    private String nickName;
    private String email;
    private UserRole role;

    public UserDto(User user) {
        id = user.getId();
        name = user.getName();
        nickName = user.getNickName();
        email = user.getEmail();
        role = user.getRole();
    }
}
