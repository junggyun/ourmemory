package myproject.ourmemory.service;

import myproject.ourmemory.domain.Group;
import myproject.ourmemory.domain.User;
import myproject.ourmemory.dto.group.AddUserRequest;
import myproject.ourmemory.dto.group.CreateGroupRequest;
import myproject.ourmemory.dto.group.UpdateGroupRequest;
import myproject.ourmemory.repository.GroupRepository;
import myproject.ourmemory.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class GroupServiceTest {

    @Autowired private UserRepository userRepository;
    @Autowired private GroupRepository groupRepository;
    @Autowired private GroupService groupService;

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
        //when
        groupService.createGroup(request);

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
        //when
        groupService.updateName(group.getId(), request);

        //then
        assertEquals("모바일과", group.getName());
    }

    @Test
    @DisplayName("그룹 삭제")
    public void 그룹_삭제() throws Exception {
        //given
        Group group = Group.builder()
                .name("컴공과")
                .build();

        groupRepository.save(group);
        //when
        groupService.deleteGroup(group.getId());

        //then
        assertEquals(0, groupRepository.count());
    }

    @Test
    @DisplayName("그룹 조회")
    public void 그룹_조회() throws Exception {
        //given
        User user = User.builder()
                .name("박정균")
                .nickName("테란킹")
                .build();

        Group group1 = Group.builder()
                .name("컴공과")
                .build();

        Group group2 = Group.builder()
                .name("모바일과")
                .build();

        userRepository.save(user);
        groupRepository.save(group1);
        groupRepository.save(group2);

        AddUserRequest request1 = AddUserRequest.builder()
                .user(user)
                .group(group1)
                .build();

        AddUserRequest request2 = AddUserRequest.builder()
                .user(user)
                .group(group2)
                .build();

        //when
        groupService.addUser(request1);
        groupService.addUser(request2);
        groupRepository.findAllWithUser();
        //then
    }

}