package myproject.ourmemory.dto.usergroup;

import lombok.Builder;
import lombok.Getter;
import myproject.ourmemory.domain.User;

import java.util.List;

@Getter
public class GetByUserResponse {

    private Long userId;
    private List<GroupList> groups;

    @Builder
    public GetByUserResponse(User user, List<GroupList> groups) {
        userId = user.getId();
        this.groups = groups;
    }
}

