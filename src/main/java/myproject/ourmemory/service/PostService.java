package myproject.ourmemory.service;

import lombok.RequiredArgsConstructor;
import myproject.ourmemory.domain.Group;
import myproject.ourmemory.domain.Post;
import myproject.ourmemory.domain.User;
import myproject.ourmemory.repository.GroupRepository;
import myproject.ourmemory.repository.PostRepository;
import myproject.ourmemory.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
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
    public Long createPost(Long userId, Long groupId, String title, String content) {
        User user = userRepository.findOne(userId);
        Group group = groupRepository.findOne(groupId);

        Post post = Post.createPost(user, group, title, content);

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
        return postRepository.findOne(postId);
    }

    //전체 게시글 조회
    public List<Post> findAllPosts() {
        return postRepository.findAll();
    }


}
