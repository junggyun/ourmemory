package myproject.ourmemory.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import myproject.ourmemory.domain.Post;
import myproject.ourmemory.domain.QGroup;
import myproject.ourmemory.domain.QPost;
import myproject.ourmemory.domain.QUser;
import myproject.ourmemory.dto.post.GetPostRequest;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements CustomPostRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final QPost qPost = QPost.post;

    @Override
    public List<Post> findPosts(GetPostRequest request) {
        return jpaQueryFactory
                .selectFrom(qPost)
                .join(qPost.group).fetchJoin()
                .join(qPost.user).fetchJoin()
                .offset(request.getOffset())
                .limit(request.getSize())
                .fetch();
    }
}
