package myproject.ourmemory.controller;

import lombok.RequiredArgsConstructor;
import myproject.ourmemory.domain.UserGroup;
import myproject.ourmemory.dto.usergroup.*;
import myproject.ourmemory.repository.GroupRepository;
import myproject.ourmemory.repository.UserRepository;
import myproject.ourmemory.service.UserGroupService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserGroupController {

    private final UserGroupService userGroupService;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    /**
     * 특정 회원 그룹 리스트 조회
     */
    @GetMapping("/userGroups/byUser")
    public GetByUserResponse findAllByUser(@ModelAttribute GetUserGroupRequest request) {

        userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        List<UserGroup> userGroups = userGroupService.listByUser(request);
        List<GroupList> collect = userGroups.stream()
                .map(u -> new GroupList(u))
                .collect(Collectors.toList());

        GetByUserResponse result = GetByUserResponse.builder()
                .userId(request.getUserId())
                .groups(collect)
                .build();

        return result;
    }

    /**
     * 특정 회원 유저 리스트 조회
     */
    @GetMapping("/userGroups/byGroup")
    public GetByGroupResponse findAllByGroup(@ModelAttribute GetUserGroupRequest request) {

        groupRepository.findById(request.getGroupId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 그룹입니다."));

        List<UserGroup> userGroups = userGroupService.listByGroup(request);
        List<UserList> collect = userGroups.stream()
                .map(u -> new UserList(u))
                .collect(Collectors.toList());

        GetByGroupResponse result = GetByGroupResponse.builder()
                .groupId(request.getGroupId())
                .users(collect)
                .build();

        return result;
    }

    /**
     * 유저그룹 등록(그룹 생성)
     */
    @PostMapping("/userGroups/create")
    public CreateUserGroupResponse create(@Valid @RequestBody CreateUserGroupRequest request) {

        Long userGroupId = userGroupService.create(request);

        return new CreateUserGroupResponse(userGroupId);
    }

    /**
     * 유저그룹 등록(그룹 입장)
     */
    @PostMapping("/userGroups/join")
    public JoingUserGroupResponse join(@Valid @RequestBody JoinUserGroupRequest request) {

        Long userGroupId = userGroupService.join(request);

        return new JoingUserGroupResponse(userGroupId);
    }
}
