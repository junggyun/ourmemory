package myproject.ourmemory.dto.comment;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateCommentRequest {

    private Long userId;
    private Long postId;
    private String content;

    public CreateCommentRequest() {
    }

    @Builder
    public CreateCommentRequest(Long userId, Long postId, String content) {
        this.userId = userId;
        this.postId = postId;
        this.content = content;
    }
}
