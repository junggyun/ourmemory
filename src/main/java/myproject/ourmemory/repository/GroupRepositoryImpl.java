package myproject.ourmemory.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import myproject.ourmemory.domain.Group;
import myproject.ourmemory.domain.QGroup;
import myproject.ourmemory.dto.group.GetGroupRequest;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class GroupRepositoryImpl implements CustomGroupRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager em;

    @Override
    public List<Group> findGroups(GetGroupRequest request) {
        return jpaQueryFactory.selectFrom(QGroup.group)
                .offset(request.getOffset())
                .limit(request.getSize())
                .fetch();
    }
}
