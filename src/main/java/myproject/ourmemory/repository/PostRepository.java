package myproject.ourmemory.repository;

import myproject.ourmemory.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>, CustomPostRepository {
    List<Post> findPostsByUserIdAndGroupId(Long userId, Long groupId);

}
