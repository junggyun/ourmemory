package myproject.ourmemory.dto.usergroup;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateUserGroupRequest {

    private Long userId;
    private String groupName;


    public CreateUserGroupRequest() {
    }

    @Builder
    public CreateUserGroupRequest(Long userId, String groupName) {
        this.userId = userId;
        this.groupName = groupName;
    }
}

