package myproject.ourmemory.dto.usergroup;

import lombok.Getter;

@Getter
public class JoingUserGroupResponse {

    private Long id;

    public JoingUserGroupResponse(Long id) {
        this.id = id;
    }
}
