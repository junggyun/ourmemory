package myproject.ourmemory.repository;

import myproject.ourmemory.domain.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserGroupRepository extends JpaRepository<UserGroup, Long>, CustomUserGroupRepository {

}
