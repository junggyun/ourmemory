package myproject.ourmemory.dto.userGroup;

import lombok.Getter;
import myproject.ourmemory.domain.User;
import myproject.ourmemory.domain.UserGroup;
import myproject.ourmemory.dto.user.UserDto;

@Getter
public class UserList {

    private UserDto user;

    public UserList() {
    }

    public UserList(UserGroup userGroup) {
        user = new UserDto(userGroup.getUser());
    }
}
