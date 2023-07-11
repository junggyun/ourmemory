package myproject.ourmemory.dto.comment;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateCommentResponse {

    private Long id;

    public CreateCommentResponse(Long id) {
        this.id = id;
    }
}
