package myproject.ourmemory.dto.usergroup;

import lombok.Getter;
import myproject.ourmemory.domain.UserGroup;
import myproject.ourmemory.domain.UserGroupRole;
import myproject.ourmemory.dto.user.UserDto;

@Getter
public class UserList {

    private Long userGroupId;
    private UserGroupRole role;
    private UserDto user;

    public UserList() {
    }

    public UserList(UserGroup userGroup) {
        userGroupId = userGroup.getId();
        role = userGroup.getRole();
        user = new UserDto(userGroup.getUser());
    }
}
