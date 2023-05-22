package myproject.ourmemory.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import myproject.ourmemory.domain.User;
import myproject.ourmemory.dto.user.CreateUserRequest;
import myproject.ourmemory.dto.user.UpdateUserRequest;
import myproject.ourmemory.repository.UserRepository;
import myproject.ourmemory.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
                .nickName("테란킹")
                .build();

        String json = objectMapper.writeValueAsString(request);
        //when
        mockMvc.perform(post("/users")
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
    @DisplayName("특정 회원 조회")
    public void 특정_회원_조회() throws Exception {
        //given
        CreateUserRequest request = CreateUserRequest.builder()
                .name("박정균")
                .nickName("테란킹")
                .build();

        Long userId = userService.join(request);
        User user = userService.findOneUser(userId);
        //expected
        mockMvc.perform(get("/users/{userId}", user.getId())
                        .characterEncoding("UTF-8")
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.name").value("박정균"))
                .andExpect(jsonPath("$.nickName").value("테란킹"))
                .andDo(print());
    }

    @Test
    @DisplayName("전체 회원 페이징 조회")
    public void 전체_회원_페이징_조회() throws Exception {
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

        //expected
        mockMvc.perform(get("/users?page=1&size=20")
                        .characterEncoding("UTF-8")
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()", is(2)))
//                .andExpect(jsonPath("$[0].id").value(user1.getId()))
//                .andExpect(jsonPath("$[0].name").value("박정균"))
//                .andExpect(jsonPath("$[0].nickName").value("테란킹"))
//                .andExpect(jsonPath("$[1].id").value(user2.getId()))
//                .andExpect(jsonPath("$[1].name").value("정한별"))
//                .andExpect(jsonPath("$[1].nickName").value("항뵬"))
                .andDo(print());
    }

    @Test
    @DisplayName("회원 닉네임 수정")
    public void 회원_닉네임_수정() throws Exception {
        //given
        User user = User.builder()
                .name("박정균")
                .nickName("테란킹")
                .build();
        userRepository.save(user);


        UpdateUserRequest request = UpdateUserRequest.builder()
                .nickName("저그킹")
                .build();

        String json = objectMapper.writeValueAsString(request);
        //when
        mockMvc.perform(post("/users/{userId}", user.getId())
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
    public void 회원_삭제() throws Exception {
        //given
        User user = User.builder()
                .name("박정균")
                .nickName("테란킹")
                .build();
        userRepository.save(user);

        //when
        mockMvc.perform(delete("/users/{userId}", user.getId())
                        .characterEncoding("UTF-8")
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print());
        //then
        assertEquals(0, userRepository.count());

    }
}