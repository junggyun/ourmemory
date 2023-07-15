package myproject.ourmemory.repository;

import myproject.ourmemory.domain.RefreshToken;
import myproject.ourmemory.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>, CustomRefreshTokenRepository {
    Optional<RefreshToken> findRefreshTokenByRefreshToken(String refreshToken);

    void deleteRefreshTokenByUser(User user);

}
