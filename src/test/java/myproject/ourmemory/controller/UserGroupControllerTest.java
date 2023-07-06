package myproject.ourmemory.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import myproject.ourmemory.domain.*;
import myproject.ourmemory.dto.usergroup.CreateUserGroupRequest;
import myproject.ourmemory.dto.usergroup.JoinUserGroupRequest;
import myproject.ourmemory.dto.usergroup.UpdateUserGroupRequest;
import myproject.ourmemory.exception.UserGroupNotFound;
import myproject.ourmemory.repository.GroupRepository;
import myproject.ourmemory.repository.UserGroupRepository;
import myproject.ourmemory.repository.UserRepository;
import myproject.ourmemory.service.UserGroupService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class UserGroupControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private UserGroupRepository userGroupRepository;
    @Autowired private UserGroupService userGroupService;
    @Autowired private UserRepository userRepository;
    @Autowired private GroupRepository groupRepository;
    @Autowired private ObjectMapper objectMapper;

    @BeforeEach
    void clean() {
        userRepository.deleteAll();
        userGroupRepository.deleteAll();

    }

    @Test
    @DisplayName("유저그룹 등록(그룹 생성)")
    @WithMockUser(username = "onlyplsson@gmail.com ", roles = "USER")
    public void 유저그룹_등록_그룹생성() throws Exception {
        //given
        User user = User.builder()
                .name("박정균")
                .email("onlyplsson@gmail.com")
                .password("123123qwe")
                .nickName("테란킹")
                .build();
        userRepository.save(user);

        CreateUserGroupRequest request = CreateUserGroupRequest.builder()
                .userId(user.getId())
                .groupName("컴소과")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //when
        mockMvc.perform(post("/api/userGroups/create")
                        .characterEncoding("UTF-8")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());

        //then
        UserGroup userGroup = userGroupRepository.findAll().get(0);

        assertEquals(UserGroupRole.HOST, userGroup.getRole());
    }

    @Test
    @DisplayName("유저그룹 등록(그룹 입장)")
    @WithMockUser(username = "onlyplsson@gmail.com ", roles = "USER")
    public void 유저그룹_등록_그룹입장() throws Exception {
        //given
        User user1 = User.builder()
                .name("박정균")
                .email("onlyplsson@gmail.com")
                .password("123123qwe")
                .nickName("테란킹")
                .build();
        userRepository.save(user1);

        User user2 = User.builder()
                .name("정한별")
                .email("wjdgksquf@gmail.com")
                .password("123123qwe")
                .nickName("항뵬")
                .build();
        userRepository.save(user2);

        CreateUserGroupRequest request1 = CreateUserGroupRequest.builder()
                .userId(user1.getId())
                .groupName("컴소과")
                .build();
        Long userGroupId = userGroupService.create(request1);
        UserGroup userGroup = userGroupRepository.findById(userGroupId).get();

        JoinUserGroupRequest request2 = JoinUserGroupRequest.builder()
                .userId(user2.getId())
                .key(userGroup.getGroup().getKey())
                .build();


        String json = objectMapper.writeValueAsString(request2);

        //when
        mockMvc.perform(post("/api/userGroups/join")
                        .characterEncoding("UTF-8")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());

        //then
        UserGroup findUserGroup = userGroupRepository.findAll().get(1);

        assertEquals(UserGroupRole.MEMBER, findUserGroup.getRole());
    }

    @Test
    @DisplayName("유저그룹 수정(ROLE)")
    @WithMockUser(username = "onlyplsson@gmail.com ", roles = "USER")
    public void 유저그룹_수정() throws Exception {
        //given
        User user1 = User.builder()
                .name("박정균")
                .email("onlyplsson@gmail.com")
                .password("123123qwe")
                .nickName("테란킹")
                .build();
        userRepository.save(user1);

        User user2 = User.builder()
                .name("정한별")
                .email("wjdgksquf@gmail.com")
                .password("123123qwe")
                .nickName("항뵬")
                .build();
        userRepository.save(user2);

        CreateUserGroupRequest request1 = CreateUserGroupRequest.builder()
                .userId(user1.getId())
                .groupName("컴공과")
                .build();
        Long hostUserGroupId = userGroupService.create(request1);

        UserGroup hostUserGroup = userGroupRepository.findById(hostUserGroupId)
                .orElseThrow(UserGroupNotFound::new);

        Long groupId = hostUserGroup.getGroup().getId();

        JoinUserGroupRequest request2 = JoinUserGroupRequest.builder()
                .userId(user2.getId())
                .key(hostUserGroup.getGroup().getKey())
                .build();
        userGroupService.join(request2);


        UpdateUserGroupRequest request3 = UpdateUserGroupRequest.builder()
                .hostUserId(user1.getId())
                .memberUserId(user2.getId())
                .build();

        String json = objectMapper.writeValueAsString(request3);

        //expected
        mockMvc.perform(post("/api/userGroups/{groupId}", groupId)
                        .characterEncoding("UTF-8")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    @DisplayName("유저그룹 삭제")
    @WithMockUser(username = "onlyplsson@gmail.com ", roles = "USER")
    public void 유저그룹_삭제() throws Exception {
        //given
        User user1 = User.builder()
                .name("박정균")
                .email("onlyplsson@gmail.com")
                .password("123123qwe")
                .nickName("테란킹")
                .build();
        userRepository.save(user1);

        User user2 = User.builder()
                .name("정한별")
                .email("wjdgksquf@gmail.com")
                .password("123123qwe")
                .nickName("항뵬")
                .build();
        userRepository.save(user2);

        CreateUserGroupRequest request1 = CreateUserGroupRequest.builder()
                .userId(user1.getId())
                .groupName("컴공과")
                .build();
        Long hostUserGroupId = userGroupService.create(request1);

        UserGroup hostUserGroup = userGroupRepository.findById(hostUserGroupId)
                .orElseThrow(UserGroupNotFound::new);

        Long groupId = hostUserGroup.getGroup().getId();

        JoinUserGroupRequest request2 = JoinUserGroupRequest.builder()
                .userId(user2.getId())
                .key(hostUserGroup.getGroup().getKey())
                .build();
        Long memberUserGroupId = userGroupService.join(request2);

        //when
        mockMvc.perform(delete("/api/userGroups/{userGroupId}", memberUserGroupId)
                        .characterEncoding("UTF-8")
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print());

        //then
        assertEquals(1, userGroupRepository.count());
    }

    @Test
    @DisplayName("특정 유저 그룹 조회")
    @WithMockUser(username = "onlyplsson@gmail.com ", roles = "USER")
    public void 특정_유저_그룹_조회() throws Exception {
        //given
        User user1 = User.builder()
                .name("박정균")
                .email("onlyplsson@gmail.com")
                .password("123123qwe")
                .nickName("테란킹")
                .build();
        userRepository.save(user1);

        User user2 = User.builder()
                .name("정한별")
                .email("wjdgksquf@gmail.com")
                .password("123123qwe")
                .nickName("항뵬")
                .build();
        userRepository.save(user2);

        CreateUserGroupRequest request1 = CreateUserGroupRequest.builder()
                .userId(user1.getId())
                .groupName("컴공과")
                .build();
        Long userGroupId = userGroupService.create(request1);
        UserGroup userGroup = userGroupRepository.findById(userGroupId).get();

        JoinUserGroupRequest request2 = JoinUserGroupRequest.builder()
                .userId(user2.getId())
                .key(userGroup.getGroup().getKey())
                .build();
        userGroupService.join(request2);


        //when
        mockMvc.perform(get("/api/userGroups/byUser/{userId}", user1.getId())
                        .characterEncoding("UTF-8")
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(user1.getId()))
                .andExpect(jsonPath("$.groups.length()").value(1))
                .andDo(print());

        //then

    }

    @Test
    @DisplayName("특정 그룹 유저 조회")
    @WithMockUser(username = "onlyplsson@gmail.com ", roles = "USER")
    public void 특정_그룹_유저_조회() throws Exception {
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

        CreateUserGroupRequest request2 = CreateUserGroupRequest.builder()
                .userId(user.getId())
                .groupName("모바일과")
                .build();
        userGroupService.create(request2);


        //when
        mockMvc.perform(get("/api/userGroups/byGroup/{groupId}", userGroup.getGroup().getId())
                        .characterEncoding("UTF-8")
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.groupId").value(userGroup.getGroup().getId()))
                .andExpect(jsonPath("$.users.length()").value(1))
                .andDo(print());

        //then

    }

}
