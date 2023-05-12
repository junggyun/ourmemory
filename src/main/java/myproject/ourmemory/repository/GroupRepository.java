package myproject.ourmemory.repository;

import lombok.RequiredArgsConstructor;
import myproject.ourmemory.domain.Group;
import myproject.ourmemory.domain.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class GroupRepository {

    private final EntityManager em;

    public void save(Group group) {
        em.persist(group);
    }

    public void delete(Group group) {
        em.remove(group);
    }

    public Group findOne(Long id) {
        return em.find(Group.class, id);
    }

    public List<Group> findAll() {
        return em.createQuery("select g from Group g", Group.class)
                .getResultList();
    }
}
