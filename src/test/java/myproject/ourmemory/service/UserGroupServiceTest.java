package myproject.ourmemory.service;

import myproject.ourmemory.InitDB;
import myproject.ourmemory.domain.User;
import myproject.ourmemory.domain.UserGroup;
import myproject.ourmemory.domain.UserGroupRole;
import myproject.ourmemory.dto.usergroup.CreateUserGroupRequest;
import myproject.ourmemory.dto.usergroup.GetUserGroupRequest;
import myproject.ourmemory.dto.usergroup.JoinUserGroupRequest;
import myproject.ourmemory.dto.usergroup.UpdateUserGroupRequest;
import myproject.ourmemory.exception.UserGroupNotFound;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

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
                .email("onlyplsson@gmail.com")
                .password("123123qwe")
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
                .nickName("저그한별")
                .build();
        userRepository.save(user2);

        CreateUserGroupRequest request1 = CreateUserGroupRequest.builder()
                .userId(user1.getId())
                .groupName("컴공과")
                .build();
        Long userGroupId1 = userGroupService.create(request1);
        UserGroup userGroup1 = userGroupRepository.findById(userGroupId1)
                .orElseThrow(UserGroupNotFound::new);

        JoinUserGroupRequest request2 = JoinUserGroupRequest.builder()
                .userId(user2.getId())
                .key(userGroup1.getGroup().getKey())
                .build();

        //when
        Long userGroupId2 = userGroupService.join(request2);

        //then
        UserGroup userGroup2 = userGroupRepository.findById(userGroupId2)
                .orElseThrow(UserGroupNotFound::new);

        assertEquals(UserGroupRole.MEMBER, userGroup2.getRole());
    }

    @Test
    @DisplayName("유저그룹 수정(ROLE)")
    public void 유저_그룹_수정() throws Exception {
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
        UserGroup memberUserGroup = userGroupRepository.findById(memberUserGroupId)
                .orElseThrow(UserGroupNotFound::new);

        UpdateUserGroupRequest request3 = UpdateUserGroupRequest.builder()
                .hostUserId(user1.getId())
                .memberUserId(user2.getId())
                .build();

        //when
        userGroupService.updateRole(groupId, request3);

        //then

        assertEquals(UserGroupRole.MEMBER, hostUserGroup.getRole());
        assertEquals(UserGroupRole.HOST, memberUserGroup.getRole());
    }

    @Test
    @DisplayName("유저그룹 삭제")
    public void 유저_그룹_삭제() throws Exception {
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
        UserGroup memberUserGroup = userGroupRepository.findById(memberUserGroupId)
                .orElseThrow(UserGroupNotFound::new);


        //when
        userGroupService.delete(memberUserGroupId);

        //then

        assertEquals(1, userGroupRepository.count());
    }

    @Test
    @DisplayName("유저 기준 조회")
    public void 유저_기준_조회() throws Exception {
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

        GetUserGroupRequest request3 = GetUserGroupRequest.builder()
                .groupId(userGroup.getGroup().getId())
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
