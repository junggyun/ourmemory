package myproject.ourmemory.controller;

import lombok.RequiredArgsConstructor;
import myproject.ourmemory.domain.Group;
import myproject.ourmemory.dto.group.*;
import myproject.ourmemory.dto.usergroup.DeleteUserGroupResponse;
import myproject.ourmemory.repository.GroupRepository;
import myproject.ourmemory.service.GroupService;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;
    private final GroupRepository groupRepository;

    /**
     * 그룹 단건 조회
     */
    @GetMapping("/groups/{groupId}")
    public GroupDto group(@PathVariable Long groupId) {
        Group group = groupService.findOneGroup(groupId);

        return new GroupDto(group);
    }

    /**
     * 그룹 페이징 조회
     */
    @GetMapping("/groups")
    public List<GroupDto> groups(@ModelAttribute GetGroupRequest request) {
        List<Group> groups = groupService.findGroups(request);
        List<GroupDto> result = groups.stream()
                .map(GroupDto::new)
                .collect(Collectors.toList());

        return result;
    }

//    /**
//     * 그룹 등록
//     */
//    @PostMapping("/groups")
//    public CreateGroupResponse createGroup(@RequestBody @Valid CreateGroupRequest request) {
//        Long groupId = groupService.createGroup(request);
//
//        return new CreateGroupResponse(groupId);
//    }

    /**
     * 그룹 수정
     */
    @PostMapping("/groups/{groupId}")
    public UpdateGroupResponse updateGroup(@PathVariable Long groupId, @RequestBody @Valid UpdateGroupRequest request) {
        groupService.updateName(groupId, request);

        return new UpdateGroupResponse(groupId);
    }

    /**
     * 그룹 삭제
     */
    @DeleteMapping("/groups/{groupId}")
    public DeleteUserGroupResponse delete(@PathVariable Long groupId) {
        groupService.deleteGroup(groupId);

        return new DeleteUserGroupResponse(groupId);
    }
}
