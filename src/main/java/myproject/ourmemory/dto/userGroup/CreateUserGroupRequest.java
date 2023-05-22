package myproject.ourmemory.dto.userGroup;

import lombok.Builder;
import lombok.Getter;
import myproject.ourmemory.domain.Group;
import myproject.ourmemory.domain.User;

@Getter
public class CreateUserGroupRequest {

    private Long userId;
    private Long groupId;

    public CreateUserGroupRequest() {
    }

    @Builder
    public CreateUserGroupRequest(Long userId, Long groupId) {
        this.userId = userId;
        this.groupId = groupId;
    }
}

