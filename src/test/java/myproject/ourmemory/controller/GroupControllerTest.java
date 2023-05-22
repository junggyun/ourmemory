package myproject.ourmemory.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import myproject.ourmemory.domain.Group;
import myproject.ourmemory.domain.User;
import myproject.ourmemory.domain.UserGroup;
import myproject.ourmemory.domain.UserGroupRole;
import myproject.ourmemory.dto.group.AddUserRequest;
import myproject.ourmemory.dto.group.CreateGroupRequest;
import myproject.ourmemory.dto.group.UpdateGroupRequest;
import myproject.ourmemory.repository.GroupRepository;
import myproject.ourmemory.repository.UserRepository;
import myproject.ourmemory.service.GroupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class GroupControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private UserRepository userRepository;
    @Autowired private GroupRepository groupRepository;
    @Autowired private GroupService groupService;
    @Autowired private ObjectMapper objectMapper;

    @BeforeEach
    void clean() {
        groupRepository.deleteAll();
    }

    @Test
    @DisplayName("그룹 등록")
    public void 그룹_등록() throws Exception {
        //given
        CreateGroupRequest request = CreateGroupRequest.builder()
                .name("컴공과")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //when
        mockMvc.perform(post("/groups")
                        .characterEncoding("UTF-8")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());
        //then
        Group group = groupRepository.findAll().get(0);

        assertEquals(1L, groupRepository.count());
        assertEquals("컴공과", group.getName());

    }

    @Test
    @DisplayName("그룹 수정")
    public void 그룹_수정() throws Exception {
        //given
        Group group = Group.builder()
                .name("컴공과")
                .build();

        groupRepository.save(group);

        UpdateGroupRequest request = UpdateGroupRequest.builder()
                .name("모바일과")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //when
        mockMvc.perform(post("/groups/{groupId}", group.getId())
                        .characterEncoding("UTF-8")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());
        //then
        assertEquals("모바일과", group.getName());

    }

    @Test
    @DisplayName("그룹 조회")
    public void 그룹_조회() throws Exception {
        //given
        User user = User.builder()
                .name("박정균")
                .nickName("테란킹")
                .build();

        User user2 = User.builder()
                .name("정한별")
                .nickName("저그한별")
                .build();

        Group group1 = Group.builder()
                .name("컴공과")
                .build();

//        Group group2 = Group.builder()
//                .name("모바일과")
//                .build();

        userRepository.save(user);
        userRepository.save(user2);
        groupRepository.save(group1);
        //groupRepository.save(group2);

        AddUserRequest request1 = AddUserRequest.builder()
                .user(user)
                .group(group1)
                .build();

        UserGroup.builder()
                .user(user)
                .group(group1)
                .role(UserGroupRole.HOST)
                .build();

        UserGroup.builder()
                .user(user2)
                .group(group1)
                .role(UserGroupRole.HOST)
                .build();


        //when
        mockMvc.perform(get("/groups")
                        .characterEncoding("UTF-8")
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print());
        //then

    }

}