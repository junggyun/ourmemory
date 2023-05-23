package myproject.ourmemory.service;

import lombok.RequiredArgsConstructor;
import myproject.ourmemory.domain.Group;
import myproject.ourmemory.domain.Post;
import myproject.ourmemory.domain.User;
import myproject.ourmemory.dto.post.CreatePostRequest;
import myproject.ourmemory.exception.GroupNotFound;
import myproject.ourmemory.exception.PostNotFound;
import myproject.ourmemory.exception.UserNotFound;
import myproject.ourmemory.repository.*;
import myproject.ourmemory.repository.GroupRepository;
import myproject.ourmemory.repository.PostRepository;
import myproject.ourmemory.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final PostRepository postRepository;

    /**
     * 게시글 등록
     */
    @Transactional
    public Long createPost(CreatePostRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(UserNotFound::new);
        Group group = groupRepository.findById(request.getGroupId())
                .orElseThrow(GroupNotFound::new);

        Post post = Post.builder()
                .user(user)
                .group(group)
                .title(request.getTitle())
                .content(request.getContent())
                .build();

        postRepository.save(post);

        return post.getId();
    }

    /**
     * 게시글 수정
     */


    /**
     * 게시글 삭제
     */
    @Transactional
    public void deletePost(Post post) {
        postRepository.delete(post);
    }

    /**
     * 게시글 조회
     */
    //특정 게시글 조회
    public Post findOnePost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(PostNotFound::new);
    }

    //전체 게시글 조회
    public List<Post> findAllPosts() {
        return postRepository.findAll();
    }


}
