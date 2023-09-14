package myproject.ourmemory.controllerdoc;

import com.fasterxml.jackson.databind.ObjectMapper;
import myproject.ourmemory.domain.User;
import myproject.ourmemory.domain.UserGroup;
import myproject.ourmemory.dto.user.CreateUserRequest;
import myproject.ourmemory.dto.usergroup.CreateUserGroupRequest;
import myproject.ourmemory.dto.usergroup.JoinUserGroupRequest;
import myproject.ourmemory.exception.UserGroupNotFound;
import myproject.ourmemory.repository.UserGroupRepository;
import myproject.ourmemory.repository.UserRepository;
import myproject.ourmemory.service.UserGroupService;
import myproject.ourmemory.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ExtendWith(RestDocumentationExtension.class)
public class UserGroupControllerDocTest {

    @Autowired private UserRepository userRepository;
    @Autowired private UserGroupRepository userGroupRepository;
    @Autowired private UserGroupService userGroupService;
    @Autowired private ObjectMapper objectMapper;

    @Autowired private MockMvc mockMvc;

    @BeforeEach
    void clean() {
        userGroupRepository.deleteAll();
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
    @DisplayName("그룹 생성")
    public void createGroup() throws Exception {
        //given
        User user = User.builder()
                .name("박아워")
                .email("our@memory.com")
                .password("our123123")
                .nickName("테란킹")
                .build();
        userRepository.save(user);

        CreateUserGroupRequest request = CreateUserGroupRequest.builder()
                .userId(user.getId())
                .groupName("개발 모임")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(post("/api/userGroups/create")
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("userGroup/{methodName}",
                        requestFields(
                                fieldWithPath("userId").description("회원 ID"),
                                fieldWithPath("groupName").description("그룹명")
                        ),
                        responseFields(
                                fieldWithPath("userGroupId").description("회원그룹 ID"),
                                fieldWithPath("role").description("그룹 권한"),
                                subsectionWithPath("group.").description("그룹 정보")
                        )
                ));

    }

    @Test
    @DisplayName("그룹 참가")
    public void joinGroup() throws Exception {
        //given
        User user = User.builder()
                .name("박아워")
                .email("our@memory.com")
                .password("our123123")
                .nickName("테란킹")
                .build();
        userRepository.save(user);

        User user2 = User.builder()
                .name("정한별")
                .email("our2@memory.com")
                .password("our123123")
                .nickName("저그킹")
                .build();
        userRepository.save(user2);

        CreateUserGroupRequest request1 = CreateUserGroupRequest.builder()
                .userId(user.getId())
                .groupName("개발 모임")
                .build();

        Long userGroupId = userGroupService.create(request1);
        UserGroup userGroup = userGroupRepository.findById(userGroupId)
                .orElseThrow(UserGroupNotFound::new);

        JoinUserGroupRequest request2 = JoinUserGroupRequest.builder()
                .userId(user2.getId())
                .key(userGroup.getGroup().getKey())
                .build();

        String json = objectMapper.writeValueAsString(request2);

        //expected
        mockMvc.perform(post("/api/userGroups/join")
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("userGroup/{methodName}",
                        requestFields(
                                fieldWithPath("userId").description("회원 ID"),
                                fieldWithPath("key").description("그룹 코드")
                        ),
                        responseFields(
                                fieldWithPath("userGroupId").description("회원그룹 ID"),
                                fieldWithPath("role").description("그룹 권한"),
                                subsectionWithPath("group.").description("그룹 정보")
                        )
                ));

    }

    @Test
    @DisplayName("그룹별 회원 조회")
    public void getUsersByGroup() throws Exception {
        //given
        User user = User.builder()
                .name("박아워")
                .email("our@memory.com")
                .password("our123123")
                .nickName("테란킹")
                .build();
        userRepository.save(user);

        User user2 = User.builder()
                .name("정한별")
                .email("our2@memory.com")
                .password("our123123")
                .nickName("저그킹")
                .build();
        userRepository.save(user2);

        CreateUserGroupRequest request1 = CreateUserGroupRequest.builder()
                .userId(user.getId())
                .groupName("개발 모임")
                .build();

        Long userGroupId = userGroupService.create(request1);
        UserGroup userGroup = userGroupRepository.findById(userGroupId)
                .orElseThrow(UserGroupNotFound::new);

        JoinUserGroupRequest request2 = JoinUserGroupRequest.builder()
                .userId(user2.getId())
                .key(userGroup.getGroup().getKey())
                .build();
        userGroupService.join(request2);

        Long groupId = userGroup.getGroup().getId();

        //expected
        mockMvc.perform(get("/api/userGroups/byGroup/{groupId}", groupId)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("userGroup/{methodName}",
                        pathParameters(
                                parameterWithName("groupId").description("그룹 ID")
                        ),
                        responseFields(
                                fieldWithPath("groupId").description("그룹 ID"),
                                subsectionWithPath("users.[]").description("회원 목록"),
                                fieldWithPath("users.[].userGroupId").description("회원그룹 ID"),
                                fieldWithPath("users.[].role").description("그룹 권한"),
                                subsectionWithPath("users.[].user").description("회원 정보")
                        )
                ));

    }

    @Test
    @DisplayName("회원별 그룹 조회")
    public void getGroupsByUser() throws Exception {
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
        userGroupService.create(request1);

        CreateUserGroupRequest request2 = CreateUserGroupRequest.builder()
                .userId(user.getId())
                .groupName("전산 모임")
                .build();
        userGroupService.create(request2);


        Long userId = user.getId();

        //expected
        mockMvc.perform(get("/api/userGroups/byUser/{userId}", userId)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("userGroup/{methodName}",
                        pathParameters(
                                parameterWithName("userId").description("회원 ID")
                        ),
                        responseFields(
                                fieldWithPath("userId").description("회원 ID"),
                                subsectionWithPath("groups.[]").description("그룹 목록"),
                                fieldWithPath("groups.[].userGroupId").description("회원그룹 ID"),
                                fieldWithPath("groups.[].role").description("그룹 권한"),
                                subsectionWithPath("groups.[].group").description("그룹 정보"),
                                fieldWithPath("groups.[].group.id").description("그룹 ID"),
                                fieldWithPath("groups.[].group.name").description("그룹명"),
                                fieldWithPath("groups.[].group.key").description("그룹 코드"),
                                fieldWithPath("groups.[].group.createdDate").description("그룹 생성 일자"),
                                fieldWithPath("groups.[].group.postCount").description("게시글 수"),
                                fieldWithPath("groups.[].group.newPostDate").description("최신 게시글 작성 일자")
                        )
                ));

    }

    @Test
    @DisplayName("그룹 탈퇴, 삭제")
    public void deleteUserGroup() throws Exception {
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

        //expected
        mockMvc.perform(delete("/api/userGroups/{userGroupId}", userGroupId)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("userGroup/{methodName}",
                        pathParameters(
                                parameterWithName("userGroupId").description("회원그룹 ID")
                        ),
                        responseFields(
                                fieldWithPath("id").description("회원그룹 ID")
                        )
                ));

    }

}
