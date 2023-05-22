package myproject.ourmemory.repository;

import myproject.ourmemory.domain.UserGroup;
import myproject.ourmemory.domain.UserGroupId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserGroupRepository extends JpaRepository<UserGroup, UserGroupId>, CustomUserGroupRepository {

}
