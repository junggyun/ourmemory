package myproject.ourmemory.service;

import myproject.ourmemory.domain.Group;
import myproject.ourmemory.domain.User;
import myproject.ourmemory.domain.UserGroup;
import myproject.ourmemory.dto.group.AddUserRequest;
import myproject.ourmemory.dto.group.CreateGroupRequest;
import myproject.ourmemory.dto.group.GetGroupRequest;
import myproject.ourmemory.dto.group.UpdateGroupRequest;
import myproject.ourmemory.dto.usergroup.CreateUserGroupRequest;
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
class GroupServiceTest {

    @Autowired private UserRepository userRepository;
    @Autowired private GroupRepository groupRepository;
    @Autowired private GroupService groupService;
    @Autowired private UserGroupService userGroupService;
    @Autowired private UserGroupRepository userGroupRepository;

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
    @DisplayName("그룹 단건 조회")
    public void 그룹_조회() throws Exception {
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
        Long userGroupId = userGroupService.create(request1);
        UserGroup userGroup = userGroupRepository.findById(userGroupId).get();
        Group group = userGroup.getGroup();


        //when
        groupService.findOneGroup(group.getId());

        //then
        assertEquals("컴공과", group.getName());
    }
        @Test
        @DisplayName("그룹 페이징 조회")
        public void 그룹_페이징_조회() throws Exception {
            //given
            User user = User.builder()
                    .name("박정균")
                    .nickName("테란킹")
                    .build();
            userRepository.save(user);

            for (int i = 1; i <= 51; i++) {
                CreateUserGroupRequest request1 = CreateUserGroupRequest.builder()
                        .userId(user.getId())
                        .groupName("컴공과" + i)
                        .build();
                userGroupService.create(request1);
            }

            GetGroupRequest request = GetGroupRequest.builder()
                    .page(1)
                    .build();

            //when
            List<Group> groups = groupService.findGroups(request);

            //then
            assertEquals(10, groups.size());
    }

}