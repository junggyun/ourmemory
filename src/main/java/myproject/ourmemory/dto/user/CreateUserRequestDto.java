package myproject.ourmemory.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
public class CreateUserRequestDto {

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickName;

    public CreateUserRequestDto() {
    }

    @Builder
    public CreateUserRequestDto(String name, String nickName) {
        this.name = name;
        this.nickName = nickName;
    }
}
