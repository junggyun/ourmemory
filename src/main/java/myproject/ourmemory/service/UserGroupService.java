package myproject.ourmemory.service;

import lombok.RequiredArgsConstructor;
import myproject.ourmemory.domain.*;
import myproject.ourmemory.dto.usergroup.CreateUserGroupRequest;
import myproject.ourmemory.dto.usergroup.GetUserGroupRequest;
import myproject.ourmemory.dto.usergroup.JoinUserGroupRequest;
import myproject.ourmemory.exception.GroupNotFound;
import myproject.ourmemory.exception.UserNotFound;
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

        UserGroup userGroup = UserGroup.builder()
                .user(user)
                .group(group)
                .role(UserGroupRole.MEMBER)
                .build();

        userGroupRepository.save(userGroup);

        return userGroup.getId();
    }

    /**
     * 유저그룹 삭제
     */

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

}
