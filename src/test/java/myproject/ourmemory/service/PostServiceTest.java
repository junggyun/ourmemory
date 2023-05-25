package myproject.ourmemory.service;

import myproject.ourmemory.domain.Post;
import myproject.ourmemory.domain.User;
import myproject.ourmemory.domain.UserGroup;
import myproject.ourmemory.dto.post.CreatePostRequest;
import myproject.ourmemory.dto.post.GetPostRequest;
import myproject.ourmemory.dto.post.UpdatePostRequest;
import myproject.ourmemory.dto.usergroup.CreateUserGroupRequest;
import myproject.ourmemory.exception.PostNotFound;
import myproject.ourmemory.repository.GroupRepository;
import myproject.ourmemory.repository.PostRepository;
import myproject.ourmemory.repository.UserGroupRepository;
import myproject.ourmemory.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired private UserRepository userRepository;
    @Autowired private GroupRepository groupRepository;
    @Autowired private PostRepository postRepository;
    @Autowired private PostService postService;
    @Autowired private UserGroupService userGroupService;
    @Autowired private UserGroupRepository userGroupRepository;

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

        Long userGroupId = userGroupService.create(request1);
        UserGroup userGroup = userGroupRepository.findById(userGroupId).get();


        CreatePostRequest request2 = CreatePostRequest.builder()
                .userId(user.getId())
                .groupId(userGroup.getGroup().getId())
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

         Long userGroupId = userGroupService.create(request1);
         UserGroup userGroup = userGroupRepository.findById(userGroupId).get();

         CreatePostRequest request2 = CreatePostRequest.builder()
                 .userId(user.getId())
                 .groupId(userGroup.getGroup().getId())
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

    @Test
    @DisplayName("게시글 페이징 조회")
    public void 게시글_페이징_조회() throws Exception {
        //given
        List<User> users = IntStream.range(1, 51)
                .mapToObj(i -> {
                    return User.builder()
                            .name("회원" + i)
                            .nickName("닉네임" + i)
                            .build();
                })
                .collect(Collectors.toList());
        userRepository.saveAll(users);

        int i = 1;
        for (User user : users) {
            CreateUserGroupRequest userGroupRequest = CreateUserGroupRequest.builder()
                    .userId(user.getId())
                    .groupName("그룹" + i)
                    .build();
            Long userGroupId = userGroupService.create(userGroupRequest);
            UserGroup userGroup = userGroupRepository.findById(userGroupId).get();
            Long groupId = userGroup.getGroup().getId();

            CreatePostRequest postRequest = CreatePostRequest.builder()
                    .userId(user.getId())
                    .groupId(groupId)
                    .title("제목" + i)
                    .content("내용" + i)
                    .build();
            postService.createPost(postRequest);

            i++;
        }

        GetPostRequest getPostRequest = GetPostRequest.builder()
                .page(1)
                .build();


        //when
        List<Post> posts = postService.findPosts(getPostRequest);

        //then
        assertEquals(10L, posts.size());
    }

    @Test
    @DisplayName("게시글 수정")
    public void 게시글_수정() throws Exception {
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

        Long userGroupId = userGroupService.create(request1);
        UserGroup userGroup = userGroupRepository.findById(userGroupId).get();

        CreatePostRequest request2 = CreatePostRequest.builder()
                .userId(user.getId())
                .groupId(userGroup.getGroup().getId())
                .title("속보")
                .content("안녕하세요")
                .build();
        Long postId = postService.createPost(request2);

        UpdatePostRequest request3 = UpdatePostRequest.builder()
                .title("뉴스")
                .content("헬로")
                .build();


        //when
        postService.updatePost(postId, request3);

        //then
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFound::new);

        assertEquals("뉴스", post.getTitle());
        assertEquals("헬로", post.getContent());
    }

    @Test
    @DisplayName("게시글 삭제")
    public void 게시글_삭제() throws Exception {
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

        Long userGroupId = userGroupService.create(request1);
        UserGroup userGroup = userGroupRepository.findById(userGroupId).get();

        CreatePostRequest request2 = CreatePostRequest.builder()
                .userId(user.getId())
                .groupId(userGroup.getGroup().getId())
                .title("속보")
                .content("안녕하세요")
                .build();
        Long postId = postService.createPost(request2);
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFound::new);

        //when
        System.out.println("before");
        postService.deletePost(post.getId());
        System.out.println("after");

        //then

        assertEquals(0, postRepository.count());

    }
}