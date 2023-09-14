package myproject.ourmemory.dto.user;

import lombok.Getter;

@Getter
public class CreateUserResponse {

    private Long id;

    public CreateUserResponse(Long id) {
        this.id = id;
    }
}
