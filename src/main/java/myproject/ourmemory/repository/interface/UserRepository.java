package myproject.ourmemory.repository;

import myproject.ourmemory.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>, CustomUserRepository {

}
