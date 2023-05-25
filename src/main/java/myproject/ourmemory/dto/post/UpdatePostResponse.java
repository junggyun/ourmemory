package myproject.ourmemory.dto.post;

import lombok.Getter;

@Getter
public class UpdatePostResponse {

    private Long id;

    public UpdatePostResponse(Long id) {
        this.id = id;
    }
}
