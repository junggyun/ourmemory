package myproject.ourmemory.repository;

import myproject.ourmemory.domain.Comment;
import myproject.ourmemory.domain.Group;
import myproject.ourmemory.dto.comment.GetByPostRequest;
import myproject.ourmemory.dto.group.GetGroupRequest;

import java.util.List;

public interface CustomCommentRepository {

    List<Comment> getCommentsByPostPaging(GetByPostRequest request);

    Long countCommentsByPost(GetByPostRequest request);
}
