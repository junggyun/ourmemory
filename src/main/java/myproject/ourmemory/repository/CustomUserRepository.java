package myproject.ourmemory.repository;

import myproject.ourmemory.domain.User;
import myproject.ourmemory.dto.user.GetUserRequest;

import java.util.List;

public interface CustomUserRepository {

    List<User> findUsers(GetUserRequest request);
}
