package myproject.ourmemory.dto.post;

import lombok.Getter;

@Getter
public class DeletePostResponse {

    private Long id;

    public DeletePostResponse(Long id) {
        this.id = id;
    }
}
