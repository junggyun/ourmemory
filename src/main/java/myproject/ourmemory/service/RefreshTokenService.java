package myproject.ourmemory.service;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import myproject.ourmemory.domain.RefreshToken;
import myproject.ourmemory.domain.Upload;
import myproject.ourmemory.domain.User;
import myproject.ourmemory.exception.RefreshTokenNotFound;
import myproject.ourmemory.exception.UserNotFound;
import myproject.ourmemory.jwt.JwtToken;
import myproject.ourmemory.repository.RefreshTokenRepository;
import myproject.ourmemory.repository.UploadRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    /**
     * 토큰 삭제
     */
    @Transactional
    public void deleteToken(User user) {
        refreshTokenRepository.deleteRefreshTokenByUser(user);
    }

    /**
     * 토큰 저장
     */
    @Transactional
    public RefreshToken save(RefreshToken refreshToken) {
        RefreshToken token = refreshTokenRepository.save(refreshToken);

        return token;
    }

    /**
     * 토큰 조회
     */
    public RefreshToken find(String refreshToken) {
        return refreshTokenRepository.findRefreshTokenByRefreshToken(refreshToken)
                .orElseThrow(RefreshTokenNotFound::new);
    }

    /**
     * 토큰 업데이트
     */
    @Transactional
    public void update(RefreshToken refreshToken, String newToken) {
        refreshToken.updateToken(newToken);
    }


}
