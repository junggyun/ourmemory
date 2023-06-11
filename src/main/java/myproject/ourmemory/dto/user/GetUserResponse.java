package myproject.ourmemory.dto.user;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class GetUserResponse {

    private int totalPages;
    private List<UserDto> users;

    public GetUserResponse() {
    }

    @Builder
    public GetUserResponse(int totalPages, List<UserDto> users) {
        this.totalPages = totalPages;
        this.users = users;
    }
}
