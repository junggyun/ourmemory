package myproject.ourmemory.dto.usergroup;

import lombok.Builder;
import lombok.Getter;
import myproject.ourmemory.domain.User;

import java.util.List;

@Getter
public class GetByUserResponse {

    private int totalPages;
    private Long userId;
    private List<GroupList> groups;

    @Builder
    public GetByUserResponse(int totalPages, User user, List<GroupList> groups) {
        this.totalPages = totalPages;
        userId = user.getId();
        this.groups = groups;
    }
}

