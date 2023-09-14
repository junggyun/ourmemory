package myproject.ourmemory.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class DeleteUserRequest {

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    public DeleteUserRequest() {
    }

    @Builder
    public DeleteUserRequest(String password) {
        this.password = password;
    }
}
