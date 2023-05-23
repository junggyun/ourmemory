package myproject.ourmemory.dto.usergroup;

import lombok.Builder;
import lombok.Getter;

@Getter
public class JoinUserGroupRequest {

    private Long userId;
    private Long groupId;


    public JoinUserGroupRequest() {
    }

    @Builder
    public JoinUserGroupRequest(Long userId, Long groupId) {
        this.userId = userId;
        this.groupId = groupId;
    }
}

