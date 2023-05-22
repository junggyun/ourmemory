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
public class UserRepositoryImpl implements CustomUserRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<User> findUsers(GetUserRequest request) {
        return jpaQueryFactory.selectFrom(QUser.user)
                .limit(request.getSize())
                .offset(request.getOffset())
                .fetch();
    }
}
