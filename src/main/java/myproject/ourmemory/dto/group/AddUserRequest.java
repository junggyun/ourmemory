package myproject.ourmemory.dto.group;

import lombok.Builder;
import lombok.Getter;
import myproject.ourmemory.domain.Group;
import myproject.ourmemory.domain.User;

@Getter
public class AddUserRequest {

    private User user;
    private Group group;

    public AddUserRequest() {
    }

    @Builder
    public AddUserRequest(User user, Group group) {
        this.user = user;
        this.group = group;
    }
}
