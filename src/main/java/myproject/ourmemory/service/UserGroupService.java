package myproject.ourmemory.service;

import lombok.RequiredArgsConstructor;
import myproject.ourmemory.domain.Group;
import myproject.ourmemory.domain.User;
import myproject.ourmemory.domain.UserGroup;
import myproject.ourmemory.domain.UserGroupRole;
import myproject.ourmemory.dto.user.GetUserRequest;
import myproject.ourmemory.dto.usergroup.CreateUserGroupRequest;
import myproject.ourmemory.dto.usergroup.GetUserGroupRequest;
import myproject.ourmemory.dto.usergroup.JoinUserGroupRequest;
import myproject.ourmemory.dto.usergroup.UpdateUserGroupRequest;
import myproject.ourmemory.exception.*;
import myproject.ourmemory.repository.GroupRepository;
import myproject.ourmemory.repository.UserGroupRepository;
import myproject.ourmemory.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserGroupService {

    private final UserGroupRepository userGroupRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    /**
     * 유저그룹 등록(그룹 생성)
     */
    @Transactional
    public Long create(CreateUserGroupRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(UserNotFound::new);

        Group group = Group.builder()
                .name(request.getGroupName())
                .build();
        groupRepository.save(group);

        UserGroup userGroup = UserGroup.builder()
                .user(user)
                .group(group)
                .role(UserGroupRole.HOST)
                .build();
        userGroupRepository.save(userGroup);

        return userGroup.getId();
    }

    /**
     * 유저그룹 등록(그룹 입장)
     */
    @Transactional
    public Long join(JoinUserGroupRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(UserNotFound::new);

        Group group = groupRepository.findById(request.getGroupId())
                .orElseThrow(GroupNotFound::new);

        joinValidate(request, user);

        UserGroup userGroup = UserGroup.builder()
                .user(user)
                .group(group)
                .role(UserGroupRole.MEMBER)
                .build();

        userGroupRepository.save(userGroup);

        return userGroup.getId();
    }



    /**
     * 유저그룹 수정(ROLE)
     */
    @Transactional
    public void updateRole(Long groupId, UpdateUserGroupRequest request) {

        GetUserGroupRequest hostRequest = GetUserGroupRequest.builder()
                .userId(request.getHostUserId())
                .groupId(groupId)
                .build();
        UserGroup hostUserGroup = userGroupRepository.findOneUserGroup(hostRequest);

        updateValidate(hostUserGroup);

        GetUserGroupRequest memberRequest = GetUserGroupRequest.builder()
                .userId(request.getMemberUserId())
                .groupId(groupId)
                .build();
        UserGroup memberUserGroup = userGroupRepository.findOneUserGroup(memberRequest);

        hostUserGroup.updateRole(memberUserGroup);
    }

    /**
     * 유저그룹 삭제(HOST)
     */
    @Transactional
    public void delete(Long userGroupId) {
        UserGroup userGroup = userGroupRepository.findById(userGroupId)
                .orElseThrow(UserGroupNotFound::new);

        deleteValidate(userGroup);

        userGroupRepository.delete(userGroup);
    }

    /**
     * 유저 기준 조회
     */
    public List<UserGroup> listByUser(GetUserGroupRequest request) {
        return userGroupRepository.findByUser(request);
    }

    /**
     * 그룹 기준 조회
     */
    public List<UserGroup> listByGroup(GetUserGroupRequest request) {
        return userGroupRepository.findByGroup(request);
    }

    /**
     * 유저 기준 그룹 페이지 수 조회
     */
    public int getPages(GetUserGroupRequest request) {
        Long totalGroups = userGroupRepository.countUsers(request);
        int totalPages = (int) Math.ceil((double) totalGroups / request.getSize());

        return totalPages;
    }

    /**
     * 예외처리
     */
    private void updateValidate(UserGroup hostUserGroup) {
        if (hostUserGroup.getRole().equals(UserGroupRole.MEMBER)) {
            throw new UserGroupNotHost("role", "HOST만 위임 가능합니다.");
        }
    }

    private void deleteValidate(UserGroup userGroup) {
        if (userGroup.getRole().equals(UserGroupRole.HOST)) {
            throw new UserGroupNotMember("role", "Member만 탈퇴 가능합니다.");
        }
    }

    private void joinValidate(JoinUserGroupRequest request, User user) {
        List<UserGroup> userGroups = user.getUserGroups();
        for (UserGroup u : userGroups) {
            if (request.getGroupId() == u.getGroup().getId()) {
                throw new UserGroupDuplicated("groupId", "이미 속한 그룹입니다.");
            }
        }
    }
}
