package myproject.ourmemory.controllerdoc;

import com.fasterxml.jackson.databind.ObjectMapper;
import myproject.ourmemory.domain.*;
import myproject.ourmemory.dto.comment.CreateCommentRequest;
import myproject.ourmemory.dto.post.CreatePostRequest;
import myproject.ourmemory.dto.post.UpdatePostRequest;
import myproject.ourmemory.dto.usergroup.CreateUserGroupRequest;
import myproject.ourmemory.exception.UserGroupNotFound;
import myproject.ourmemory.repository.CommentRepository;
import myproject.ourmemory.repository.PostRepository;
import myproject.ourmemory.repository.UserGroupRepository;
import myproject.ourmemory.repository.UserRepository;
import myproject.ourmemory.service.UserGroupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.io.FileInputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ExtendWith(RestDocumentationExtension.class)
public class CommentControllerDocTest {

    @Autowired private UserRepository userRepository;
    @Autowired private UserGroupRepository userGroupRepository;
    @Autowired private UserGroupService userGroupService;
    @Autowired private PostRepository postRepository;
    @Autowired private CommentRepository commentRepository;
    @Autowired private ObjectMapper objectMapper;

    @Autowired private MockMvc mockMvc;

    @BeforeEach
    void clean() {
        commentRepository.deleteAll();
    }

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .apply(documentationConfiguration(restDocumentation)
                        .uris().withScheme("https").withHost("ourmemory.api.com").withPort(443)
                        .and()
                        .operationPreprocessors()
                        .withRequestDefaults(prettyPrint())
                        .withResponseDefaults(prettyPrint())
                )
                .build();

    }

    @Test
    @DisplayName("댓글 작성")
    public void createComment() throws Exception {
        //given
        User user = User.builder()
                .name("박아워")
                .email("our@memory.com")
                .password("our123123")
                .nickName("테란킹")
                .build();
        userRepository.save(user);

        CreateUserGroupRequest request1 = CreateUserGroupRequest.builder()
                .userId(user.getId())
                .groupName("개발 모임")
                .build();

        Long userGroupId = userGroupService.create(request1);
        UserGroup userGroup = userGroupRepository.findById(userGroupId)
                .orElseThrow(UserGroupNotFound::new);

        List<Upload> uploads = new ArrayList<>();
        URL resource = getClass().getResource("/image/test.jpg");
        String fileName = "test.jpg";
        MockMultipartFile file = new MockMultipartFile("file", fileName, "image/jpg", new FileInputStream(resource.getFile()));
        Upload upload = Upload.builder()
                .fileName(fileName)
                .filePath("/image/" + fileName)
                .fileSize(file.getSize())
                .build();
        uploads.add(upload);


        Post post = Post.builder()
                .user(user)
                .group(userGroup.getGroup())
                .title("속보")
                .content("안녕하세요")
                .uploads(uploads)
                .build();
        postRepository.save(post);

        Long postId = post.getId();

        CreateCommentRequest request = CreateCommentRequest.builder()
                .postId(postId)
                .userId(user.getId())
                .content("잘봤습니다!")
                .build();
        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(post("/api/comments")
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("comment/{methodName}",
                        requestFields(
                                fieldWithPath("userId").description("회원 ID"),
                                fieldWithPath("postId").description("게시글 ID"),
                                fieldWithPath("content").description("댓글 내용")
                        ),
                        responseFields(
                                fieldWithPath("id").description("댓글 ID")
                        )
                ));

    }

    @Test
    @DisplayName("댓글 삭제")
    public void deleteComment() throws Exception {
        //given
        User user = User.builder()
                .name("박아워")
                .email("our@memory.com")
                .password("our123123")
                .nickName("테란킹")
                .build();
        userRepository.save(user);

        CreateUserGroupRequest request1 = CreateUserGroupRequest.builder()
                .userId(user.getId())
                .groupName("개발 모임")
                .build();

        Long userGroupId = userGroupService.create(request1);
        UserGroup userGroup = userGroupRepository.findById(userGroupId)
                .orElseThrow(UserGroupNotFound::new);

        List<Upload> uploads = new ArrayList<>();
        URL resource = getClass().getResource("/image/test.jpg");
        String fileName = "test.jpg";
        MockMultipartFile file = new MockMultipartFile("file", fileName, "image/jpg", new FileInputStream(resource.getFile()));
        Upload upload = Upload.builder()
                .fileName(fileName)
                .filePath("/image/" + fileName)
                .fileSize(file.getSize())
                .build();
        uploads.add(upload);


        Post post = Post.builder()
                .user(user)
                .group(userGroup.getGroup())
                .title("속보")
                .content("안녕하세요")
                .uploads(uploads)
                .build();
        postRepository.save(post);

        Comment comment = Comment.builder()
                .user(user)
                .post(post)
                .content("잘봤습니다!")
                .build();
        commentRepository.save(comment);

        //expected
        mockMvc.perform(delete("/api/comments/{commentId}", comment.getId())
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("comment/{methodName}",
                        pathParameters(
                                parameterWithName("commentId").description("댓글 ID")
                        )
                ));

    }

    @Test
    @DisplayName("댓글 단건 조회")
    public void getComment() throws Exception {
        //given
        User user = User.builder()
                .name("박아워")
                .email("our@memory.com")
                .password("our123123")
                .nickName("테란킹")
                .build();
        userRepository.save(user);

        CreateUserGroupRequest request1 = CreateUserGroupRequest.builder()
                .userId(user.getId())
                .groupName("개발 모임")
                .build();

        Long userGroupId = userGroupService.create(request1);
        UserGroup userGroup = userGroupRepository.findById(userGroupId)
                .orElseThrow(UserGroupNotFound::new);

        List<Upload> uploads = new ArrayList<>();
        URL resource = getClass().getResource("/image/test.jpg");
        String fileName = "test.jpg";
        MockMultipartFile file = new MockMultipartFile("file", fileName, "image/jpg", new FileInputStream(resource.getFile()));
        Upload upload = Upload.builder()
                .fileName(fileName)
                .filePath("/image/" + fileName)
                .fileSize(file.getSize())
                .build();
        uploads.add(upload);


        Post post = Post.builder()
                .user(user)
                .group(userGroup.getGroup())
                .title("속보")
                .content("안녕하세요")
                .uploads(uploads)
                .build();
        postRepository.save(post);

        Comment comment = Comment.builder()
                .user(user)
                .post(post)
                .content("잘봤습니다!")
                .build();
        commentRepository.save(comment);

        //expected
        mockMvc.perform(get("/api/comments/{commentId}", comment.getId())
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("comment/{methodName}",
                        pathParameters(
                                parameterWithName("commentId").description("댓글 ID")
                        ),
                        responseFields(
                                fieldWithPath("commentId").description("댓글 ID"),
                                fieldWithPath("postId").description("게시글 ID"),
                                fieldWithPath("userNickName").description("회원 닉네임"),
                                fieldWithPath("content").description("댓글 내용"),
                                fieldWithPath("createdDate").description("댓글 작성 일자")
                        )
                ));
    }

    @Test
    @DisplayName("게시글별 댓글 목록 조회")
    public void getCommentsByPost() throws Exception {
        //given
        User user = User.builder()
                .name("박아워")
                .email("our@memory.com")
                .password("our123123")
                .nickName("테란킹")
                .build();
        userRepository.save(user);

        CreateUserGroupRequest request1 = CreateUserGroupRequest.builder()
                .userId(user.getId())
                .groupName("개발 모임")
                .build();

        Long userGroupId = userGroupService.create(request1);
        UserGroup userGroup = userGroupRepository.findById(userGroupId)
                .orElseThrow(UserGroupNotFound::new);

        List<Upload> uploads = new ArrayList<>();
        URL resource = getClass().getResource("/image/test.jpg");
        String fileName = "test.jpg";
        MockMultipartFile file = new MockMultipartFile("file", fileName, "image/jpg", new FileInputStream(resource.getFile()));
        Upload upload = Upload.builder()
                .fileName(fileName)
                .filePath("/image/" + fileName)
                .fileSize(file.getSize())
                .build();
        uploads.add(upload);


        Post post = Post.builder()
                .user(user)
                .group(userGroup.getGroup())
                .title("속보")
                .content("안녕하세요")
                .uploads(uploads)
                .build();
        postRepository.save(post);

        Comment comment = Comment.builder()
                .user(user)
                .post(post)
                .content("잘봤습니다!")
                .build();
        commentRepository.save(comment);

        //expected
        mockMvc.perform(get("/api/comments/byPost?postId={postId}&size=10&page=1", post.getId())
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("comment/{methodName}",
                        queryParameters(
                                parameterWithName("postId").description("게시글 ID"),
                                parameterWithName("size").description("페이지 당 댓글 수"),
                                parameterWithName("page").description("페이지")
                        ),
                        responseFields(
                                fieldWithPath("postId").description("게시글 ID"),
                                fieldWithPath("totalPages").description("총 페이지 수"),
                                fieldWithPath("totalCount").description("총 댓글 수"),
                                subsectionWithPath("comments").description("댓글 목록")
                        )
                ));

    }


}
