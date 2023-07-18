package myproject.ourmemory.dto.usergroup;

import lombok.Getter;

@Getter
public class JoingUserGroupResponse {

    private Long id;
    private Long groupId;

    public JoingUserGroupResponse(Long id, Long groupId) {
        this.id = id;
        this.groupId = groupId;
    }
}
