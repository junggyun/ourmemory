package myproject.ourmemory.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import myproject.ourmemory.domain.Comment;
import myproject.ourmemory.domain.QComment;
import myproject.ourmemory.domain.QUser;
import myproject.ourmemory.domain.User;
import myproject.ourmemory.dto.comment.GetByPostRequest;
import myproject.ourmemory.dto.user.GetUserRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CustomCommentRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final QComment qComment = QComment.comment;


    @Override
    public List<Comment> getCommentsByPostPaging(GetByPostRequest request) {
        return jpaQueryFactory
                .selectFrom(qComment)
                .join(qComment.post)
                .where(qComment.post.id.eq(request.getPostId()))
                .offset(request.getOffset())
                .limit(request.getSize())
                .orderBy(qComment.id.desc())
                .fetch();
    }

    @Override
    public Long countCommentsByPost(GetByPostRequest request) {
        return jpaQueryFactory
                .select(qComment.count())
                .from(qComment)
                .where(qComment.post.id.eq(request.getPostId()))
                .fetchFirst();
    }
}
