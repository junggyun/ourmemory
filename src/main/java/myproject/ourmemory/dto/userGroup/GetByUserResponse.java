package myproject.ourmemory.dto.userGroup;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class GetByUserResponse {

    private Long userId;
    private List<GroupList> groups;

    @Builder
    public GetByUserResponse(Long userId, List<GroupList> groups) {
        this.userId = userId;
        this.groups = groups;
    }
}

