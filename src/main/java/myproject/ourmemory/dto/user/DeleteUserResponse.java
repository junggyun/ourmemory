package myproject.ourmemory.dto.user;

import lombok.Getter;

@Getter
public class DeleteUserResponse {

    private Long id;

    public DeleteUserResponse(Long id) {
        this.id = id;
    }
}
