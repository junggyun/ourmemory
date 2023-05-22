package myproject.ourmemory.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import myproject.ourmemory.domain.Group;
import myproject.ourmemory.domain.User;
import myproject.ourmemory.dto.userGroup.CreateUserGroupRequest;
import myproject.ourmemory.repository.GroupRepository;
import myproject.ourmemory.repository.UserGroupRepository;
import myproject.ourmemory.repository.UserRepository;
import myproject.ourmemory.service.UserGroupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

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
        userGroupRepository.deleteAll();

    }

    @Test
    @DisplayName("유저그룹 등록")
    public void 유저그룹_등록() throws Exception {
        //given
        User user = User.builder()
                .name("박정균")
                .nickName("테란킹")
                .build();

        Group group = Group.builder()
                .name("컴공과")
                .build();

        userRepository.save(user);
        groupRepository.save(group);

        CreateUserGroupRequest request = CreateUserGroupRequest.builder()
                .userId(user.getId())
                .groupId(group.getId())
                .build();

        String json = objectMapper.writeValueAsString(request);

        //when
        mockMvc.perform(post("/userGroups")
                        .characterEncoding("UTF-8")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());

        //then

     }

    @Test
    @DisplayName("특정 유저 그룹 조회")
    public void 특정_유저_그룹_조회() throws Exception {
        //given
        User user1 = User.builder()
                .name("박정균")
                .nickName("테란킹")
                .build();
        userRepository.save(user1);

        User user2 = User.builder()
                .name("정한별")
                .nickName("저그한별")
                .build();
        userRepository.save(user2);

        Group group = Group.builder()
                .name("컴공과")
                .build();

        groupRepository.save(group);

        CreateUserGroupRequest request1 = CreateUserGroupRequest.builder()
                .userId(user1.getId())
                .groupId(group.getId())
                .build();
        userGroupService.create(request1);

        CreateUserGroupRequest request2 = CreateUserGroupRequest.builder()
                .userId(user2.getId())
                .groupId(group.getId())
                .build();
        userGroupService.create(request2);


        //when
        mockMvc.perform(get("/userGroups/byUser?userId={userId}&size=5&page=1", user1.getId())
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
    public void 특정_그룹_유저_조회() throws Exception {
        //given
        User user = User.builder()
                .name("박정균")
                .nickName("테란킹")
                .build();
        userRepository.save(user);

        Group group1 = Group.builder()
                .name("컴공과")
                .build();
        groupRepository.save(group1);

        Group group2 = Group.builder()
                .name("모바일과")
                .build();
        groupRepository.save(group2);

        CreateUserGroupRequest request1 = CreateUserGroupRequest.builder()
                .userId(user.getId())
                .groupId(group1.getId())
                .build();
        userGroupService.create(request1);

        CreateUserGroupRequest request2 = CreateUserGroupRequest.builder()
                .userId(user.getId())
                .groupId(group2.getId())
                .build();
        userGroupService.create(request2);


        //when
        mockMvc.perform(get("/userGroups/byGroup?groupId={groupId}&size=5&page=1", group1.getId())
                        .characterEncoding("UTF-8")
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.groupId").value(group1.getId()))
                .andExpect(jsonPath("$.users.length()").value(1))
                .andDo(print());

        //then

    }

}