package myproject.ourmemory.dto.usergroup;

import lombok.Builder;
import lombok.Getter;
import myproject.ourmemory.domain.Group;
import myproject.ourmemory.dto.group.GroupDto;
import myproject.ourmemory.dto.user.UserDto;

import java.util.List;

@Getter
public class GetByGroupResponse {

    private Long groupId;
    private List<UserList> users;

    @Builder
    public GetByGroupResponse(Group group, List<UserList> users) {
        groupId = group.getId();
        this.users = users;
    }
}
