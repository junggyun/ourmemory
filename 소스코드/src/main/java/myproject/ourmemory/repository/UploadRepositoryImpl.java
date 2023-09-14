package myproject.ourmemory.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import myproject.ourmemory.domain.QUpload;
import myproject.ourmemory.domain.Upload;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UploadRepositoryImpl implements CustomUploadRepository {

    private final JPAQueryFactory jpaQueryFactory;
    QUpload qUpload = QUpload.upload;

    @Override
    public List<Upload> findByPost(Long postId) {
        return jpaQueryFactory
                .selectFrom(qUpload)
                .join(qUpload.post).fetchJoin()
                .where(qUpload.post.id.eq(postId))
                .orderBy(qUpload.post.id.asc())
                .fetch();
    }
}
