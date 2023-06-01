package myproject.ourmemory.dto.post;

import lombok.Builder;
import lombok.Getter;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;

@Getter
public class UpdatePostRequest {

    @NotBlank(message = "닉네임을 입력해주세요.")
    private String title;

    @Lob
    @NotBlank(message = "닉네임을 입력해주세요.")
    private String content;

    public UpdatePostRequest() {
    }

    @Builder
    public UpdatePostRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
