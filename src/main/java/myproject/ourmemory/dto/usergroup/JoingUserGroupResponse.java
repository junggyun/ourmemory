package myproject.ourmemory.dto.usergroup;

import lombok.Getter;
import myproject.ourmemory.domain.UserGroupId;

@Getter
public class JoingUserGroupResponse {

    private UserGroupId id;

    public JoingUserGroupResponse(UserGroupId id) {
        this.id = id;
    }
}
