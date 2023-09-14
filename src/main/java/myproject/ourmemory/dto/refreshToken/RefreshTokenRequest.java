package myproject.ourmemory.dto.refreshToken;

import lombok.Getter;

@Getter
public class RefreshTokenRequest {

    private String refreshToken;

    public RefreshTokenRequest() {
    }

    public RefreshTokenRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
