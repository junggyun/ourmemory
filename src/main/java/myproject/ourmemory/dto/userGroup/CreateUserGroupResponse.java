package myproject.ourmemory.dto.userGroup;

import lombok.Getter;
import myproject.ourmemory.domain.UserGroupId;

@Getter
public class CreateUserGroupResponse {

    private UserGroupId id;

    public CreateUserGroupResponse(UserGroupId id) {
        this.id = id;
    }
}
