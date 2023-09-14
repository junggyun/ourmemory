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
public class UploadControllerDocTest {

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
    @DisplayName("게시글별 파일 목록 조회")
    public void getUploadsByPost() throws Exception {
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
        mockMvc.perform(get("/api/uploads/{postId}", postId)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("upload/{methodName}",
                        pathParameters(
                                parameterWithName("postId").description("게시글 ID")
                        ),
                        responseFields(
                                fieldWithPath("[].id").description("파일 ID"),
                                fieldWithPath("[].fileName").description("파일명"),
                                fieldWithPath("[].filePath").description("파일 경로")
                        )
                ));

    }

}
