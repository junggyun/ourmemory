package myproject.ourmemory.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import myproject.ourmemory.domain.Post;
import myproject.ourmemory.domain.Upload;
import myproject.ourmemory.domain.User;
import myproject.ourmemory.domain.UserGroup;
import myproject.ourmemory.dto.post.CreatePostRequest;
import myproject.ourmemory.dto.post.GetPostRequest;
import myproject.ourmemory.dto.post.UpdatePostRequest;
import myproject.ourmemory.dto.usergroup.CreateUserGroupRequest;
import myproject.ourmemory.repository.GroupRepository;
import myproject.ourmemory.repository.PostRepository;
import myproject.ourmemory.repository.UserGroupRepository;
import myproject.ourmemory.repository.UserRepository;
import myproject.ourmemory.service.PostService;
import myproject.ourmemory.service.UserGroupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private UserRepository userRepository;
    @Autowired private GroupRepository groupRepository;
    @Autowired private UserGroupService userGroupService;
    @Autowired private PostRepository postRepository;
    @Autowired private PostService postService;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private UserGroupRepository userGroupRepository;

    @BeforeEach
    void clean() {
        userRepository.deleteAll();
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("게시글 등록")
    @WithMockUser(username = "onlyplsson@gmail.com ", roles = "USER")
    public void 게시글_등록() throws Exception {
        //given
        User user = User.builder()
                .name("박정균")
                .email("onlyplsson@gmail.com")
                .password("123123qwe")
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

        String json = objectMapper.writeValueAsString(request2);

        MockMultipartFile request = new MockMultipartFile("request", "request", "application/json", json.getBytes(StandardCharsets.UTF_8));

        //when
        mockMvc.perform(multipart("/api/posts")
                        .file(request)
                )
                .andExpect(status().isOk())
                .andDo(print());
        //then
        Post post = postRepository.findAll().get(0);

        assertEquals(1L, postRepository.count());

    }

    @Test
    @DisplayName("게시글 단건 조회")
    @WithMockUser(username = "onlyplsson@gmail.com ", roles = "USER")
    public void 게시글_단건_조회() throws Exception {
        //given
        User user = User.builder()
                .name("박정균")
                .email("onlyplsson@gmail.com")
                .password("123123qwe")
                .nickName("테란킹")
                .build();
        userRepository.save(user);

        CreateUserGroupRequest request1 = CreateUserGroupRequest.builder()
                .userId(user.getId())
                .groupName("컴공과")
                .build();

        Long userGroupId = userGroupService.create(request1);
        UserGroup userGroup = userGroupRepository.findById(userGroupId).get();

        Post post = Post.builder()
                .user(user)
                .group(userGroup.getGroup())
                .title("속보")
                .content("안녕하세요")
                .build();
        postRepository.save(post);

        //expected
        mockMvc.perform(get("/api/posts/{postId}", post.getId())
                        .characterEncoding("UTF-8")
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.nickName").value("테란킹"))
                .andExpect(jsonPath("$.title").value("속보"))
                .andExpect(jsonPath("$.content").value("안녕하세요"))
                .andDo(print());

     }

    @Test
    @DisplayName("그룹별 게시글 페이징 조회")
    @WithMockUser(username = "onlyplsson@gmail.com ", roles = "USER")
    public void 그룹별_게시글_페이징_조회() throws Exception {
        //given
        User user = User.builder()
                .name("박정균")
                .email("onlyplsson@gmail.com")
                .password("123123qwe")
                .nickName("테란킹")
                .build();
        userRepository.save(user);

        CreateUserGroupRequest request1 = CreateUserGroupRequest.builder()
                .userId(user.getId())
                .groupName("컴공과")
                .build();

        Long userGroupId = userGroupService.create(request1);
        UserGroup userGroup = userGroupRepository.findById(userGroupId).get();

        List<Post> posts = IntStream.range(1, 51)
                .mapToObj(i -> {
                    return Post.builder()
                            .user(user)
                            .group(userGroup.getGroup())
                            .title("속보" + i)
                            .content("안녕하세요" + i)
                            .build();
                })
                .collect(Collectors.toList());
        postRepository.saveAll(posts);

        //expected
        mockMvc.perform(get("/api/posts/byGroup?&groupId={groupId}&size=10&page=1", userGroup.getGroup().getId())
                        .characterEncoding("UTF-8")
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.posts.length()").value(10))
                .andDo(print());

    }

    @Test
    @DisplayName("게시글 수정")
    @WithMockUser(username = "onlyplsson@gmail.com ", roles = "USER")
    public void 게시글_수정() throws Exception {
        //given
        User user = User.builder()
                .name("박정균")
                .email("onlyplsson@gmail.com")
                .password("123123qwe")
                .nickName("테란킹")
                .build();
        userRepository.save(user);

        CreateUserGroupRequest request1 = CreateUserGroupRequest.builder()
                .userId(user.getId())
                .groupName("컴공과")
                .build();

        Long userGroupId = userGroupService.create(request1);
        UserGroup userGroup = userGroupRepository.findById(userGroupId).get();

        Post post = Post.builder()
                .user(user)
                .group(userGroup.getGroup())
                .title("속보")
                .content("안녕하세요")
                .build();
        postRepository.save(post);

        UpdatePostRequest request3 = UpdatePostRequest.builder()
                .title("뉴스")
                .content("헬로")
                .build();
        String json = objectMapper.writeValueAsString(request3);

        //expected
        mockMvc.perform(post("/api/posts/{postId}", post.getId())
                        .characterEncoding("UTF-8")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());

        assertEquals("뉴스", post.getTitle());
        assertEquals("헬로", post.getContent());
    }

    @Test
    @DisplayName("게시글 삭제")
    @WithMockUser(username = "onlyplsson@gmail.com ", roles = "USER")
    public void 게시글_삭제() throws Exception {
        //given
        User user = User.builder()
                .name("박정균")
                .email("onlyplsson@gmail.com")
                .password("123123qwe")
                .nickName("테란킹")
                .build();
        userRepository.save(user);

        CreateUserGroupRequest request1 = CreateUserGroupRequest.builder()
                .userId(user.getId())
                .groupName("컴공과")
                .build();

        Long userGroupId = userGroupService.create(request1);
        UserGroup userGroup = userGroupRepository.findById(userGroupId).get();

        Post post = Post.builder()
                .user(user)
                .group(userGroup.getGroup())
                .title("속보")
                .content("안녕하세요")
                .build();
        postRepository.save(post);

        //expected
        mockMvc.perform(delete("/api/posts/{postId}", post.getId())
                        .characterEncoding("UTF-8")
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print());

        assertEquals(0, postRepository.count());
    }

}
