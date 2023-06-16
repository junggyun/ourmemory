package myproject.ourmemory.dto.post;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class GetPostByGroupResponse {

    private int totalPages;
    private List<PostDto> posts;

    @Builder
    public GetPostByGroupResponse(int totalPages, List<PostDto> posts) {
        this.totalPages = totalPages;
        this.posts = posts;
    }
}
