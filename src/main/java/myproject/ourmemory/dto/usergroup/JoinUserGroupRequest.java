package myproject.ourmemory.dto.usergroup;

import lombok.Builder;
import lombok.Getter;

@Getter
public class JoinUserGroupRequest {

    private Long userId;
    private Long groupId;
    private String key;
    //todo gruopId 삭제해야됨

    public JoinUserGroupRequest() {
    }

    @Builder
    public JoinUserGroupRequest(Long userId, Long groupId, String key) {
        this.userId = userId;
        this.groupId = groupId;
        this.key = key;
    }
}

