package myproject.ourmemory.repository;

import myproject.ourmemory.domain.Post;
import myproject.ourmemory.dto.post.GetPostRequest;
import myproject.ourmemory.dto.post.PostDto;

import java.util.List;

public interface CustomPostRepository {

    List<Post> findPosts(GetPostRequest request);

    List<Post> findPostsByGroup(GetPostRequest request);

    Long countPostsByGroup(GetPostRequest request);
}
