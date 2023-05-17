package myproject.ourmemory.dto.user;

import lombok.Getter;
import myproject.ourmemory.domain.User;

@Getter
public class UserDto {

    private Long id;
    private String name;
    private String nickName;

    public UserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.nickName = user.getNickName();
    }
}
