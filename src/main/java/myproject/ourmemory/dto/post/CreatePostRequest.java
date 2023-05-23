package myproject.ourmemory.dto.post;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class CreatePostRequest {

    private Long userId;
    private Long groupId;

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    public CreatePostRequest() {
    }

    @Builder
    public CreatePostRequest(Long userId, Long groupId, String title, String content) {
        this.userId = userId;
        this.groupId = groupId;
        this.title = title;
        this.content = content;
    }
}
