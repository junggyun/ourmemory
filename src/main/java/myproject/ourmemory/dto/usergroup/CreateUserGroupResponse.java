package myproject.ourmemory.dto.usergroup;

import lombok.Getter;
import myproject.ourmemory.domain.UserGroupId;

@Getter
public class CreateUserGroupResponse {

    private UserGroupId id;

    public CreateUserGroupResponse(UserGroupId id) {
        this.id = id;
    }
}
