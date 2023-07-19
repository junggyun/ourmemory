package myproject.ourmemory.controllerdoc;

import com.fasterxml.jackson.databind.ObjectMapper;
import myproject.ourmemory.dto.user.CreateUserRequest;
import myproject.ourmemory.dto.user.UpdateUserRequest;
import myproject.ourmemory.repository.UserRepository;
import myproject.ourmemory.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
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
import static org.springframework.restdocs.operation.preprocess.Preprocessors.modifyUris;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ExtendWith(RestDocumentationExtension.class)
public class UserControllerDocTest {

    @Autowired private UserRepository userRepository;
    @Autowired private UserService userService;
    @Autowired private ObjectMapper objectMapper;

    @Autowired private MockMvc mockMvc;

    @BeforeEach
    void clean() {
        userRepository.deleteAll();
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
    @DisplayName("회원 가입")
    public void join() throws Exception {
        //given
        CreateUserRequest request = CreateUserRequest.builder()
                .name("박아워")
                .email("our@memory.com")
                .password("our123123")
                .nickName("테란킹")
                .build();
        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(post("/api/users")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                        .accept(APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("user/{methodName}",
                        requestFields(
                                fieldWithPath("name").description("이름"),
                                fieldWithPath("email").description("이메일"),
                                fieldWithPath("password").description("비밀번호"),
                                fieldWithPath("nickName").description("닉네임")
                        ),
                        responseFields(
                                fieldWithPath("id").description("회원 ID")
                        )
                ));

    }

    @Test
    @DisplayName("회원 단건 조회")
    public void getUser() throws Exception {
        //given
        CreateUserRequest request = CreateUserRequest.builder()
                .name("박아워")
                .email("our@memory.com")
                .password("our123123")
                .nickName("테란킹")
                .build();

        Long userId = userService.join(request);
        //expected
        mockMvc.perform(get("/api/users/{userId}", userId)

                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("user/{methodName}",
                        pathParameters(
                                parameterWithName("userId").description("회원 ID")
                        ),
                        responseFields(
                                fieldWithPath("id").description("회원 ID"),
                                fieldWithPath("name").description("회원명"),
                                fieldWithPath("nickName").description("회원 닉네임"),
                                fieldWithPath("email").description("회원 이메일"),
                                fieldWithPath("createdDate").description("회원 가입 일자"),
                                fieldWithPath("role").description("계정 권한")
                        )
                ));

     }

    @Test
    @DisplayName("로그인")
    public void login() throws Exception {
        //given
        CreateUserRequest request = CreateUserRequest.builder()
                .name("박아워")
                .email("our@memory.com")
                .password("our123123")
                .nickName("테란킹")
                .build();
        userService.join(request);

        String loginRequest = "{\"email\": \"our@memory.com\", \"password\": \"our123123\"}";

        //expected
        mockMvc.perform(post("/api/users/login", loginRequest)
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(loginRequest)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("user/{methodName}",
                        requestFields(
                                fieldWithPath("email").description("이메일"),
                                fieldWithPath("password").description("비밀번호")
                        ),
                        responseFields(
                                fieldWithPath("grantType").description("인증 방식"),
                                fieldWithPath("accessToken").description("액세스 토큰"),
                                fieldWithPath("refreshToken").description("리프레쉬 토큰")
                        )
                ));

    }

    @Test
    @DisplayName("로그아웃")
    public void logout() throws Exception {
        //given
        CreateUserRequest request = CreateUserRequest.builder()
                .name("박아워")
                .email("our@memory.com")
                .password("our123123")
                .nickName("테란킹")
                .build();
        Long userId = userService.join(request);

        userService.login(request.getEmail(), request.getPassword());

        //expected
        mockMvc.perform(delete("/api/users/logout/{userId}", userId)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("user/{methodName}",
                        pathParameters(
                                parameterWithName("userId").description("회원 ID")
                        )
                ));

    }

    @Test
    @DisplayName("회원 정보 수정")
    public void editUser() throws Exception {
        //given
        CreateUserRequest request = CreateUserRequest.builder()
                .name("박아워")
                .email("our@memory.com")
                .password("our123123")
                .nickName("테란킹")
                .build();
        Long userId = userService.join(request);

        UpdateUserRequest request1 = UpdateUserRequest.builder()
                .nickName("저그킹")
                .build();

        String json = objectMapper.writeValueAsString(request1);


        //expected
        mockMvc.perform(post("/api/users/{userId}", userId)
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("user/{methodName}",
                        pathParameters(
                                parameterWithName("userId").description("회원 ID")
                        ),
                        requestFields(
                                fieldWithPath("nickName").description("회원 닉네임")
                        ),
                        responseFields(
                                fieldWithPath("id").description("회원 ID")
                        )
                ));

    }

    @Test
    @DisplayName("회원 탈퇴")
    public void deleteUser() throws Exception {
        //given
        CreateUserRequest request = CreateUserRequest.builder()
                .name("박아워")
                .email("our@memory.com")
                .password("our123123")
                .nickName("테란킹")
                .build();

        Long userId = userService.join(request);
        //expected
        mockMvc.perform(delete("/api/users/{userId}", userId)

                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("user/{methodName}",
                        pathParameters(
                                parameterWithName("userId").description("회원 ID")
                        ),
                        responseFields(
                                fieldWithPath("id").description("회원 ID")
                        )
                ));

    }

}
