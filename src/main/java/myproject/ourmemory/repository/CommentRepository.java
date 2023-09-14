package myproject.ourmemory.repository;

import myproject.ourmemory.domain.Comment;
import myproject.ourmemory.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long>, CustomCommentRepository {


}
