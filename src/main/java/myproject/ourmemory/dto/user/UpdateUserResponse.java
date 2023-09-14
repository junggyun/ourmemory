package myproject.ourmemory.dto.user;

import lombok.Getter;
import myproject.ourmemory.domain.User;

@Getter
public class UpdateUserResponse {

    private Long id;

    public UpdateUserResponse(Long id) {
        this.id = id;
    }
}
