package myproject.ourmemory.dto.userGroup;

import lombok.Getter;
import myproject.ourmemory.domain.UserGroup;
import myproject.ourmemory.domain.UserGroupRole;
import myproject.ourmemory.dto.group.GroupDto;

import java.util.List;

@Getter
public class GroupList {

    private UserGroupRole role;
    private GroupDto group;


    public GroupList(UserGroup userGroup) {
        role = userGroup.getRole();
        group = new GroupDto(userGroup.getGroup());


    }
}
