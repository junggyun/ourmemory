package myproject.ourmemory.dto.usergroup;

import lombok.Getter;

@Getter
public class CreateUserGroupResponse {

    private Long id;

    public CreateUserGroupResponse(Long id) {
        this.id = id;
    }
}
