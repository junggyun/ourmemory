package myproject.ourmemory.controllerdoc;

import com.fasterxml.jackson.databind.ObjectMapper;
import myproject.ourmemory.domain.Post;
import myproject.ourmemory.domain.Upload;
import myproject.ourmemory.domain.User;
import myproject.ourmemory.domain.UserGroup;
import myproject.ourmemory.dto.post.CreatePostRequest;
import myproject.ourmemory.dto.post.UpdatePostRequest;
import myproject.ourmemory.dto.usergroup.CreateUserGroupRequest;
import myproject.ourmemory.exception.UserGroupNotFound;
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
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ExtendWith(RestDocumentationExtension.class)
public class PostControllerDocTest {

    @Autowired private UserRepository userRepository;
    @Autowired private UserGroupRepository userGroupRepository;
    @Autowired private PostRepository postRepository;
    @Autowired private UserGroupService userGroupService;
    @Autowired private ObjectMapper objectMapper;

    @Autowired private MockMvc mockMvc;

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .apply(documentationConfiguration(restDocumentation)
                        .uris().withScheme("https").withHost("ourmemory.shop").withPort(443)
                        .and()
                        .operationPreprocessors()
                        .withRequestDefaults(prettyPrint())
                        .withResponseDefaults(prettyPrint())
                )
                .build();

    }

    @Test
    @DisplayName("게시글 작성")
    public void createPost() throws Exception {
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

        CreatePostRequest request2 = CreatePostRequest.builder()
                .userId(user.getId())
                .groupId(userGroup.getGroup().getId())
                .title("속보")
                .content("안녕하세요")
                .build();

        URL resource = getClass().getResource("/image/test.jpg");
        String fileName = "test.jpg";

        String json = objectMapper.writeValueAsString(request2);

        MockMultipartFile request = new MockMultipartFile("request", "request", "application/json", json.getBytes(StandardCharsets.UTF_8));
        MockMultipartFile file = new MockMultipartFile("file", fileName, "image/jpeg", new FileInputStream(resource.getFile()));

        //expected
        mockMvc.perform(multipart("/api/posts")
                        .file(request)
                        .file(file)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("post/{methodName}",
                        relaxedRequestParts(
                                partWithName("request").description("회원 ID, 그룹 ID, 제목, 내용"),
                                partWithName("file").description("파일").optional()
                        ),
                        responseFields(
                                fieldWithPath("id").description("게시글 ID")
                        )
                ));

    }

    @Test
    @DisplayName("게시글 단건 조회")
    public void getPost() throws Exception {
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

        //expected
        mockMvc.perform(get("/api/posts/{postId}", postId)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("post/{methodName}",
                        pathParameters(
                                parameterWithName("postId").description("게시글 ID")
                        ),
                        responseFields(
                                fieldWithPath("postId").description("게시글 ID"),
                                subsectionWithPath("user").description("회원 정보"),
                                fieldWithPath("title").description("게시글 제목"),
                                fieldWithPath("content").description("게시글 내용"),
                                fieldWithPath("createdDate").description("게시글 작성 일자"),
                                fieldWithPath("modifiedDate").description("게시글 수정 일자"),
                                fieldWithPath("viewCount").description("게시글 조회 수"),
                                fieldWithPath("commentCount").description("게시글 댓글 수"),
                                fieldWithPath("newCommentDate").description("게시글 최신 댓글 일자"),
                                subsectionWithPath("uploads").description("파일 목록")
                        )
                ));

    }

    @Test
    @DisplayName("그룹별 게시글 목록 조회")
    public void getPostsByGroup() throws Exception {
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

        Long groupId = userGroup.getGroup().getId();

        //expected
        mockMvc.perform(get("/api/posts/byGroup?groupId={groupId}&size=10&page=1", groupId)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("post/{methodName}",
                        queryParameters(
                                parameterWithName("groupId").description("그룹 ID"),
                                parameterWithName("size").description("페이지 당 게시글 수"),
                                parameterWithName("page").description("페이지")
                        ),
                        responseFields(
                                fieldWithPath("totalPages").description("총 페이지 수"),
                                fieldWithPath("groupId").description("그룹 ID"),
                                subsectionWithPath("posts").description("게시글 목록")
                        )
                ));

    }

    @Test
    @DisplayName("게시글 수정")
    public void editPost() throws Exception {
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

        UpdatePostRequest request = UpdatePostRequest.builder()
                .title("질문")
                .content("궁금합니다")
                .build();

        Long postId = post.getId();
        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(post("/api/posts/{postId}", postId)
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("post/{methodName}",
                        pathParameters(
                                parameterWithName("postId").description("게시글 ID")
                        ),
                        requestFields(
                                fieldWithPath("title").description("제목"),
                                fieldWithPath("content").description("내용")
                        ),
                        responseFields(
                                fieldWithPath("id").description("게시글 ID")
                        )
                ));

    }

    @Test
    @DisplayName("게시글 삭제")
    public void deletePost() throws Exception {
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

        //expected
        mockMvc.perform(delete("/api/posts/{postId}", postId)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("post/{methodName}",
                        pathParameters(
                                parameterWithName("postId").description("게시글 ID")
                        ),
                        responseFields(
                                fieldWithPath("id").description("게시글 ID")
                        )
                ));

    }
}
