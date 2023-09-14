package myproject.ourmemory.dto.usergroup;

import lombok.Getter;
import myproject.ourmemory.domain.UserGroup;
import myproject.ourmemory.domain.UserGroupRole;
import myproject.ourmemory.dto.group.GroupDto;

@Getter
public class GroupList {

    private Long userGroupId;
    private UserGroupRole role;
    private GroupDto group;


    public GroupList(UserGroup userGroup) {
        userGroupId = userGroup.getId();
        role = userGroup.getRole();
        group = new GroupDto(userGroup.getGroup());


    }
}
