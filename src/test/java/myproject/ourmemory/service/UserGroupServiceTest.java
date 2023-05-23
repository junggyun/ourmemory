package myproject.ourmemory.service;

import myproject.ourmemory.InitDB;
import myproject.ourmemory.domain.Group;
import myproject.ourmemory.domain.User;
import myproject.ourmemory.domain.UserGroup;
import myproject.ourmemory.domain.UserGroupId;
import myproject.ourmemory.dto.usergroup.CreateUserGroupRequest;
import myproject.ourmemory.dto.usergroup.GetUserGroupRequest;
import myproject.ourmemory.dto.usergroup.JoinUserGroupRequest;
import myproject.ourmemory.repository.GroupRepository;
import myproject.ourmemory.repository.UserGroupRepository;
import myproject.ourmemory.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserGroupServiceTest {

    @Autowired private UserGroupRepository userGroupRepository;
    @Autowired private UserGroupService userGroupService;
    @Autowired private UserRepository userRepository;
    @Autowired private GroupRepository groupRepository;
    @Autowired private InitDB initDB;

    @BeforeEach
    void clean() {
        userGroupRepository.deleteAll();

    }

    @Test
    @DisplayName("유저그룹 등록(그룹 생성)")
    public void 유저_그룹_등록_그룹생성() throws Exception {
        //given
        User user = User.builder()
                .name("박정균")
                .nickName("테란킹")
                .build();
        userRepository.save(user);

        CreateUserGroupRequest request = CreateUserGroupRequest.builder()
                .userId(user.getId())
                .groupName("컴소과")
                .build();

        //when
        userGroupService.create(request);

        //then
        UserGroup userGroup = userGroupRepository.findAll().get(0);

        assertEquals("HOST", userGroup.getRole().toString());
     }

    @Test
    @DisplayName("유저그룹 등록(그룹 입장)")
    public void 유저_그룹_등록_그룹입장() throws Exception {
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

        JoinUserGroupRequest request = JoinUserGroupRequest.builder()
                .userId(user.getId())
                .groupId(group.getId())
                .build();

        //when
        userGroupService.join(request);

        //then
        UserGroup userGroup = userGroupRepository.findAll().get(0);

        assertEquals("MEMBER", userGroup.getRole().toString());
    }

    @Test
    @DisplayName("유저 기준 조회")
    public void 유저_기준_조회() throws Exception {
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


        CreateUserGroupRequest request1 = CreateUserGroupRequest.builder()
                .userId(user1.getId())
                .groupName("컴공과")
                .build();
        UserGroupId userGroupId = userGroupService.create(request1);

        JoinUserGroupRequest request2 = JoinUserGroupRequest.builder()
                .userId(user2.getId())
                .groupId(userGroupId.getGroupId())
                .build();
        userGroupService.join(request2);

        GetUserGroupRequest request3 = GetUserGroupRequest.builder()
                .userId(user1.getId())
                .build();
        //when
        List<UserGroup> userGroups = userGroupService.listByUser(request3);

        //then
        assertEquals(1L,userGroups.size());
        assertEquals("박정균", userGroups.get(0).getUser().getName());
        assertEquals("테란킹", userGroups.get(0).getUser().getNickName());
        assertEquals("컴공과", userGroups.get(0).getGroup().getName());
        assertEquals("HOST", userGroups.get(0).getRole().toString());
    }

    @Test
    @DisplayName("그룹 기준 조회")
    public void 그룹_기준_조회() throws Exception {
        //given
        User user = User.builder()
                .name("박정균")
                .nickName("테란킹")
                .build();
        userRepository.save(user);

        CreateUserGroupRequest request1 = CreateUserGroupRequest.builder()
                .userId(user.getId())
                .groupName("컴공과")
                .build();
        UserGroupId userGroupId = userGroupService.create(request1);

        CreateUserGroupRequest request2 = CreateUserGroupRequest.builder()
                .userId(user.getId())
                .groupName("모바일과")
                .build();
        userGroupService.create(request2);

        GetUserGroupRequest request3 = GetUserGroupRequest.builder()
                .groupId(userGroupId.getGroupId())
                .build();
        //when
        List<UserGroup> userGroups = userGroupService.listByGroup(request3);

        //then
        assertEquals(1L,userGroups.size());
        assertEquals("박정균", userGroups.get(0).getUser().getName());
        assertEquals("테란킹", userGroups.get(0).getUser().getNickName());
        assertEquals("컴공과", userGroups.get(0).getGroup().getName());
        assertEquals("HOST", userGroups.get(0).getRole().toString());
    }

}