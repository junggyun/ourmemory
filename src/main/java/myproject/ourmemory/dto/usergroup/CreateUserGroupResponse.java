package myproject.ourmemory.dto.usergroup;

import lombok.Getter;

@Getter
public class CreateUserGroupResponse {

    private Long id;
    private Long groupId;

    public CreateUserGroupResponse(Long id, Long groupId) {
        this.id = id;
        this.groupId = groupId;
    }
}
