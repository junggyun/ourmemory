package myproject.ourmemory.service;

import lombok.RequiredArgsConstructor;
import myproject.ourmemory.domain.*;
import myproject.ourmemory.dto.userGroup.CreateUserGroupRequest;
import myproject.ourmemory.dto.userGroup.GetUserGroupRequest;
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
     * 유저그룹 생성
     */
    @Transactional
    public UserGroupId create(CreateUserGroupRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        Group group = groupRepository.findById(request.getGroupId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 그룹입니다."));

        UserGroup userGroup = UserGroup.builder()
                .user(user)
                .group(group)
                .role(UserGroupRole.HOST)
                .build();

        userGroupRepository.save(userGroup);

        return userGroup.getId();
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

}
