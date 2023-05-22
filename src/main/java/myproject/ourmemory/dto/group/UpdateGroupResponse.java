package myproject.ourmemory.dto.group;

import lombok.Getter;

@Getter
public class UpdateGroupResponse {

    private Long id;

    public UpdateGroupResponse(Long id) {
        this.id = id;
    }
}
