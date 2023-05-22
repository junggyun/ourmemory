package myproject.ourmemory.dto.userGroup;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class GetByGroupResponse {

    private Long groupId;
    private List<UserList> users;

    @Builder
    public GetByGroupResponse(Long groupId, List<UserList> users) {
        this.groupId = groupId;
        this.users = users;
    }
}
