package myproject.ourmemory.dto.comment;

import lombok.Builder;
import lombok.Getter;
import myproject.ourmemory.domain.Comment;

import java.util.List;

@Getter
public class GetByPostResponse {

    private Long postId;
    private int totalPages;
    private Long totalCount;
    private List<CommentDto> comments;

    @Builder
    public GetByPostResponse(Long postId, int totalPages, Long totalCount, List<CommentDto> comments) {
        this.postId = postId;
        this.totalPages = totalPages;
        this.totalCount = totalCount;
        this.comments = comments;
    }
}
