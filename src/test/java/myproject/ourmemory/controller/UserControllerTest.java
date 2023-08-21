package myproject.ourmemory.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import myproject.ourmemory.domain.User;
import myproject.ourmemory.dto.user.CreateUserRequest;
import myproject.ourmemory.dto.user.DeleteUserRequest;
import myproject.ourmemory.dto.user.UpdateUserRequest;
import myproject.ourmemory.repository.UserRepository;
import myproject.ourmemory.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private UserRepository userRepository;
    @Autowired private UserService userService;
    @Autowired private ObjectMapper objectMapper;

    @BeforeEach
    void clean() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원 등록")
    public void 회원_등록() throws Exception {
        //given
        CreateUserRequest request = CreateUserRequest.builder()
                .name("박정균")
                .email("onlyplsson@gmail.com")
                .password("123123qwe")
                .nickName("테란킹")
                .build();

        String json = objectMapper.writeValueAsString(request);
        //when
        mockMvc.perform(post("/api/users")
                        .characterEncoding("UTF-8")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());
        //then
        User user = userRepository.findAll().get(0);

        assertEquals(1, userRepository.findAll().size());
        assertEquals("박정균", user.getName());
        assertEquals("테란킹", user.getNickName());

    }

    @Test
    @DisplayName("로그인")
    public void 로그인() throws Exception {
        //given
        CreateUserRequest request = CreateUserRequest.builder()
                .name("박정균")
                .email("onlyplsson@gmail.com")
                .password("123123qwe")
                .nickName("테란킹")
                .build();
        userService.join(request);

        String loginRequest = "{\"email\": \"onlyplsson@gmail.com\", \"password\": \"123123qwe\"}";

        //expected
        mockMvc.perform(post("/api/users/login")
                        .characterEncoding("UTF-8")
                        .contentType(APPLICATION_JSON)
                        .content(loginRequest)
                )
                .andExpect(status().isOk())
                .andDo(print());
     }

    @Test
    @DisplayName("회원 등록 - 닉네임 중복")
    public void 회원_등록_닉네임_중복_예외처리() throws Exception {
        //given
        User user1 = User.builder()
                .name("정한별")
                .email("wjdgksquf@gmail.com")
                .password("123123qwe")
                .nickName("테란킹")
                .build();
        userRepository.save(user1);

        CreateUserRequest request = CreateUserRequest.builder()
                .name("박정균")
                .email("onlyplsson@gmail.com")
                .password("123123qwe")
                .nickName("테란킹")
                .build();

        String json = objectMapper.writeValueAsString(request);
        //expected
        mockMvc.perform(post("/api/users")
                        .characterEncoding("UTF-8")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isConflict())
                .andDo(print());


    }

    @Test
    @DisplayName("특정 회원 조회")
    @WithMockUser(username = "onlyplsson@gmail.com ", roles = "USER")
    public void 특정_회원_조회() throws Exception {
        //given
        CreateUserRequest request = CreateUserRequest.builder()
                .name("박정균")
                .email("onlyplsson@gmail.com")
                .password("123123qwe")
                .nickName("테란킹")
                .build();

        Long userId = userService.join(request);
        //expected
        mockMvc.perform(get("/api/users/{userId}", userId)
                        .characterEncoding("UTF-8")
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.name").value("박정균"))
                .andExpect(jsonPath("$.nickName").value("테란킹"))
                .andDo(print());
    }

    @Test
    @DisplayName("존재하지 않는 회원 조회")
    @WithMockUser(username = "onlyplsson@gmail.com ", roles = "USER")
    public void 존재하지_않는_회원_조회() throws Exception {
        //given

        //expected
        mockMvc.perform(get("/api/users/{userId}", 3L)
                        .characterEncoding("UTF-8")
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("회원 닉네임 수정")
    @WithMockUser(username = "onlyplsson@gmail.com ", roles = "USER")
    public void 회원_닉네임_수정() throws Exception {
        //given
        User user = User.builder()
                .name("박정균")
                .email("onlyplsson@gmail.com")
                .password("123123qwe")
                .nickName("테란킹")
                .build();
        userRepository.save(user);


        UpdateUserRequest request = UpdateUserRequest.builder()
                .nickName("저그킹")
                .build();

        String json = objectMapper.writeValueAsString(request);
        //when
        mockMvc.perform(post("/api/users/{userId}", user.getId())
                        .characterEncoding("UTF-8")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());
        //then
        assertEquals("저그킹", user.getNickName());

    }

    @Test
    @DisplayName("회원 삭제")
    @WithMockUser(username = "onlyplsson@gmail.com ", roles = "USER")
    public void 회원_삭제() throws Exception {
        //given
        CreateUserRequest request = CreateUserRequest.builder()
                .name("박정균")
                .email("onlyplsson@gmail.com")
                .password("1234")
                .nickName("테란킹")
                .build();
        Long userId = userService.join(request);


        //when
        mockMvc.perform(delete("/api/users/{userId}", userId)
                        .param("password", "1234")
                )
                .andExpect(status().isOk())
                .andDo(print());
        //then
        assertEquals(0, userRepository.count());

    }
}
