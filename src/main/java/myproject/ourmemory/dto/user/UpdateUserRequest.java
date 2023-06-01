package myproject.ourmemory.dto.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Builder;
import lombok.Getter;

import jakarta.validation.constraints.NotBlank;

@Getter
public class UpdateUserRequest {

    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickName;

    public UpdateUserRequest() {
    }

    @Builder
    public UpdateUserRequest(String nickName) {
        this.nickName = nickName;
    }
}
