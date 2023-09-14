package myproject.ourmemory.dto.group;

import lombok.Builder;
import lombok.Getter;
import myproject.ourmemory.domain.User;

import jakarta.validation.constraints.NotBlank;

@Getter
public class CreateGroupRequest {

    @NotBlank(message = "그룹명을 입력해주세요.")
    private String name;

    public CreateGroupRequest() {
    }

    @Builder
    public CreateGroupRequest(String name) {
        this.name = name;
    }
}
