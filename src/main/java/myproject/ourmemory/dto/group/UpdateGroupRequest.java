package myproject.ourmemory.dto.group;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class UpdateGroupRequest {

    @NotBlank(message = "그룹명을 입력해주세요.")
    private String name;

    public UpdateGroupRequest() {
    }

    @Builder
    public UpdateGroupRequest(String name) {
        this.name = name;
    }
}
