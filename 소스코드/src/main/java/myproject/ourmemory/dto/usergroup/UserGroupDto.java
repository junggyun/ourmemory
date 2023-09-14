package myproject.ourmemory.dto.usergroup;

import lombok.Getter;
import myproject.ourmemory.domain.UserGroup;
import myproject.ourmemory.domain.UserGroupRole;
import myproject.ourmemory.dto.group.GroupDto;
import myproject.ourmemory.dto.user.UserDto;

@Getter
public class UserGroupDto {

    private Long id;
    private UserGroupRole role;
    private UserDto user;
    private GroupDto group;

    public UserGroupDto(UserGroup userGroup) {
        id = userGroup.getId();
        user = new UserDto(userGroup.getUser());
        group = new GroupDto(userGroup.getGroup());
        role = userGroup.getRole();
    }
}
