package myproject.ourmemory.dto.post;

import lombok.Getter;

@Getter
public class CreatePostResponse {

    private Long id;

    public CreatePostResponse() {
    }

    public CreatePostResponse(Long id) {
        this.id = id;
    }
}
