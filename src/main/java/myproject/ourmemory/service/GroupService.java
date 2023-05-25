package myproject.ourmemory.service;

import lombok.RequiredArgsConstructor;
import myproject.ourmemory.domain.Group;
import myproject.ourmemory.domain.UserGroup;
import myproject.ourmemory.domain.UserGroupRole;
import myproject.ourmemory.dto.group.AddUserRequest;
import myproject.ourmemory.dto.group.CreateGroupRequest;
import myproject.ourmemory.dto.group.GetGroupRequest;
import myproject.ourmemory.dto.group.UpdateGroupRequest;
import myproject.ourmemory.exception.GroupNotFound;
import myproject.ourmemory.repository.GroupRepository;
import myproject.ourmemory.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GroupService {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    /**
     * 그룹 등록
     */
    @Transactional
    public Long createGroup(CreateGroupRequest request) {

        Group group = Group.builder()
                .name(request.getName())
                .build();

        groupRepository.save(group);

        return group.getId();
    }

    @Transactional
    public void addUser(AddUserRequest request) {

        UserGroup userGroup = UserGroup.builder()
                .user(request.getUser())
                .group(request.getGroup())
                .role(UserGroupRole.MEMBER)
                .build();
    }

    /**
     * 그룹 수정
     */
    @Transactional
    public void updateName(Long groupId, UpdateGroupRequest request) {
        Group findGroup = groupRepository.findById(groupId)
                .orElseThrow(GroupNotFound::new);

        findGroup.updateName(request);
    }

    /**
     * 그룹 삭제
     */
    @Transactional
    public void deleteGroup(Long groupId) {
        Group findGroup = groupRepository.findById(groupId)
                .orElseThrow(GroupNotFound::new);

        groupRepository.delete(findGroup);
    }

    /**
     * 그룹 조회
     */
    //특정 그룹 조회
    public Group findOneGroup(Long groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(GroupNotFound::new);
    }

    //전체 그룹 페이징 조회
    public List<Group> findGroups(GetGroupRequest request) {
        return groupRepository.findGroups(request);
    }

    /**
     * 예외 처리
     */
    @Transactional
    public void validateDuplicateGroupKey(Group group) {
        List<Group> findGroups = groupRepository.findAll();
        List<String> keys = new ArrayList<>();
        for (Group findGroup : findGroups) {
            keys.add(findGroup.getKey());
        }
        while (true) {
            if (keys.contains(group.getKey())) {
                group.setKey();
                continue;
            }
            break;
        }
    }
}
