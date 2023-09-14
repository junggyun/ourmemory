package myproject.ourmemory.dto.usergroup;

import lombok.Getter;
import myproject.ourmemory.domain.UserGroup;

@Getter
public class UpdateUserGroupResponse {

    private UserGroupDto memberToHost;
    private UserGroupDto hostToMember;

    public UpdateUserGroupResponse() {
    }

    public UpdateUserGroupResponse(UserGroup hostUserGroup, UserGroup memberUserGroup) {
        this.memberToHost = new UserGroupDto(hostUserGroup);
        this.hostToMember = new UserGroupDto(memberUserGroup);
    }
}
