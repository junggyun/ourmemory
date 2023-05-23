package myproject.ourmemory.service;

import myproject.ourmemory.domain.Post;
import myproject.ourmemory.domain.User;
import myproject.ourmemory.dto.post.CreatePostRequest;
import myproject.ourmemory.dto.usergroup.CreateUserGroupRequest;
import myproject.ourmemory.exception.PostNotFound;
import myproject.ourmemory.repository.GroupRepository;
import myproject.ourmemory.repository.PostRepository;
import myproject.ourmemory.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired private UserRepository userRepository;
    @Autowired private GroupRepository groupRepository;
    @Autowired private PostRepository postRepository;
    @Autowired private PostService postService;
    @Autowired private UserGroupService userGroupService;

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("게시글 등록")
    public void 게시글_등록() throws Exception {
        //given
        User user = User.builder()
                .name("박정균")
                .nickName("테란킹")
                .build();
        userRepository.save(user);

        CreateUserGroupRequest request1 = CreateUserGroupRequest.builder()
                .userId(user.getId())
                .groupName("컴공과")
                .build();

        Long groupId = userGroupService.create(request1).getGroupId();

        CreatePostRequest request2 = CreatePostRequest.builder()
                .userId(user.getId())
                .groupId(groupId)
                .title("속보")
                .content("안녕하세요")
                .build();
        //when
        Long postId = postService.createPost(request2);

        //then
        assertEquals(1L, postRepository.count());
     }
     
     @Test
     @DisplayName("게시글 단건 조회")
     public void 게시글_단건_조회() throws Exception {
         //given
         User user = User.builder()
                 .name("박정균")
                 .nickName("테란킹")
                 .build();
         userRepository.save(user);

         CreateUserGroupRequest request1 = CreateUserGroupRequest.builder()
                 .userId(user.getId())
                 .groupName("컴공과")
                 .build();

         Long groupId = userGroupService.create(request1).getGroupId();

         CreatePostRequest request2 = CreatePostRequest.builder()
                 .userId(user.getId())
                 .groupId(groupId)
                 .title("속보")
                 .content("안녕하세요")
                 .build();
         Long postId = postService.createPost(request2);

         //when
         Post post = postService.findOnePost(postId);

         //then
         assertEquals("박정균", post.getUser().getName());
         assertEquals("테란킹", post.getUser().getNickName());
         assertEquals("컴공과", post.getGroup().getName());
         assertEquals("속보", post.getTitle());
         assertEquals("안녕하세요", post.getContent());
      }

}