package myproject.ourmemory.controller;

import lombok.RequiredArgsConstructor;
import myproject.ourmemory.domain.UserGroup;
import myproject.ourmemory.domain.UserGroupId;
import myproject.ourmemory.dto.userGroup.*;
import myproject.ourmemory.service.UserGroupService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserGroupController {

    private final UserGroupService userGroupService;
    private final UserRepository userRepository;

    @PostMapping("/userGroups")
    public CreateUserGroupResponse create(@RequestBody CreateUserGroupRequest request) {

        UserGroupId userGroupId = userGroupService.create(request);

        return new CreateUserGroupResponse(userGroupId);
    }

    /**
     * 특정 유저 그룹 리스트 조회
     */
    @GetMapping("/userGroups")
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
}
