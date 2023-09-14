package myproject.ourmemory.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import myproject.ourmemory.domain.QUser;
import myproject.ourmemory.domain.User;
import myproject.ourmemory.dto.user.GetUserRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepositoryImpl implements CustomRefreshTokenRepository {

}
