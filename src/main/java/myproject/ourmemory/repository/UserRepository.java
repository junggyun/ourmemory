package myproject.ourmemory.repository;

import lombok.RequiredArgsConstructor;
import myproject.ourmemory.domain.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    public void save(User user) {
        em.persist(user);
    }

    public void saveAll(List<User> users) {

    }

    public void delete(User user) {
        em.remove(user);
    }

    public void deleteAll() {
        em.clear();
    }

    public User findOne(Long id) {
        return em.find(User.class, id);
    }

    public List<User> findAll() {
        return em.createQuery("select u from User u", User.class)
                .getResultList();
    }
}
