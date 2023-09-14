package myproject.ourmemory.dto.user;

import lombok.Builder;
import lombok.Getter;

import jakarta.validation.constraints.NotBlank;

@Getter
public class CreateUserRequest {

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickName;

    public CreateUserRequest() {
    }

    @Builder
    public CreateUserRequest(String email, String password, String name, String nickName) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickName = nickName;
    }
}
