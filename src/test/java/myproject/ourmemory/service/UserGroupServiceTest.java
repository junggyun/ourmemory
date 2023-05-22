package myproject.ourmemory.service;

import myproject.ourmemory.domain.Group;
import myproject.ourmemory.domain.User;
import myproject.ourmemory.domain.UserGroup;
import myproject.ourmemory.dto.userGroup.CreateUserGroupRequest;
import myproject.ourmemory.dto.userGroup.GetUserGroupRequest;
import myproject.ourmemory.repository.GroupRepository;
import myproject.ourmemory.repository.UserGroupRepository;
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
class UserGroupServiceTest {

    @Autowired private UserGroupRepository userGroupRepository;
    @Autowired private UserGroupService userGroupService;
    @Autowired private UserRepository userRepository;
    @Autowired private GroupRepository groupRepository;

    @BeforeEach
    void clean() {
        userGroupRepository.deleteAll();

    }

    @Test
    @DisplayName("유저 그룹 생성")
    public void 유저_그룹_생성() throws Exception {
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

        //when
        userGroupService.create(request);

        //then
        UserGroup userGroup = userGroupRepository.findAll().get(0);

        assertEquals("HOST", userGroup.getRole().toString());
     }

    @Test
    @DisplayName("유저 기준 조회")
    public void 유저_기준_조회() throws Exception {
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

        CreateUserGroupRequest request1 = CreateUserGroupRequest.builder()
                .userId(user.getId())
                .groupId(group.getId())
                .build();

        userGroupService.create(request1);

        GetUserGroupRequest request2 = GetUserGroupRequest.builder()
                .userId(user.getId())
                .build();
        //when
        userGroupService.listByUser(request2);

        //then

    }

}