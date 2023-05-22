package myproject.ourmemory.dto.user;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class CreateUserRequest {

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickName;

    public CreateUserRequest() {
    }

    @Builder
    public CreateUserRequest(String name, String nickName) {
        this.name = name;
        this.nickName = nickName;
    }
}
