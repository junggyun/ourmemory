package myproject.ourmemory.dto.post;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class GetPostByGroupResponse {

    private int totalPages;
    private Long groupId;
    private List<PostDto> posts;

    @Builder
    public GetPostByGroupResponse(int totalPages, Long groupId, List<PostDto> posts) {
        this.totalPages = totalPages;
        this.groupId = groupId;
        this.posts = posts;
    }
}
