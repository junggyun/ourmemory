package myproject.ourmemory.dto.group;

import lombok.Getter;

@Getter
public class CreateGroupResponse {

    private Long id;

    public CreateGroupResponse(Long id) {
        this.id = id;
    }
}
