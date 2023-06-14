package myproject.ourmemory.repository;

import myproject.ourmemory.domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long>, CustomGroupRepository {

    Optional<Group> findByKey(String Key);

}
