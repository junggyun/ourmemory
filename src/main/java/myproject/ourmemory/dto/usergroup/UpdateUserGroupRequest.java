package myproject.ourmemory.dto.usergroup;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdateUserGroupRequest {

    private Long hostUserId;
    private Long memberUserId;

    public UpdateUserGroupRequest() {
    }

    @Builder
    public UpdateUserGroupRequest(Long hostUserId, Long memberUserId) {
        this.hostUserId = hostUserId;
        this.memberUserId = memberUserId;
    }
}