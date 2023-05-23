package myproject.ourmemory.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import myproject.ourmemory.domain.Post;
import myproject.ourmemory.domain.User;
import myproject.ourmemory.dto.post.CreatePostRequest;
import myproject.ourmemory.dto.usergroup.CreateUserGroupRequest;
import myproject.ourmemory.repository.GroupRepository;
import myproject.ourmemory.repository.PostRepository;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

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

    @BeforeEach
    void clean() {
        userRepository.deleteAll();
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

        String json = objectMapper.writeValueAsString(request2);

        //when
        mockMvc.perform(post("/posts")
                        .characterEncoding("UTF-8")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());
        //then
        Post post = postRepository.findAll().get(0);

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

        //expected
        mockMvc.perform(get("/posts/{postId}", postId)
                        .characterEncoding("UTF-8")
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.name").value("박정균"))
                .andExpect(jsonPath("$.user.nickName").value("테란킹"))
                .andExpect(jsonPath("$.group.name").value("컴공과"))
                .andExpect(jsonPath("$.title").value("속보"))
                .andExpect(jsonPath("$.content").value("안녕하세요"))
                .andDo(print());

     }

}