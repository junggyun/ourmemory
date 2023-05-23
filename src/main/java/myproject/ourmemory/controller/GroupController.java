package myproject.ourmemory.controller;

import lombok.RequiredArgsConstructor;
import myproject.ourmemory.domain.Group;
import myproject.ourmemory.dto.group.*;
import myproject.ourmemory.dto.usergroup.DeleteUserGroupResponse;
import myproject.ourmemory.repository.GroupRepository;
import myproject.ourmemory.service.GroupService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;
    private final GroupRepository groupRepository;

    @PostMapping("/groups")
    public CreateGroupResponse createGroup(@RequestBody @Valid CreateGroupRequest request) {
        Long groupId = groupService.createGroup(request);

        return new CreateGroupResponse(groupId);
    }

    @PostMapping("/groups/{groupId}")
    public UpdateGroupResponse updateGroup(@PathVariable Long groupId, @RequestBody @Valid UpdateGroupRequest request) {
        groupService.updateName(groupId, request);

        return new UpdateGroupResponse(groupId);
    }

    @GetMapping("/groups")
    public List<GroupDto> groups() {
        List<Group> groups = groupRepository.findAllWithUser();
        List<GroupDto> result = groups.stream()
                .map(g -> new GroupDto(g))
                .collect(Collectors.toList());

        return result;
    }

    @DeleteMapping("/groups/{groupId}")
    public DeleteUserGroupResponse delete(@PathVariable Long groupId) {
        groupService.deleteGroup(groupId);

        return new DeleteUserGroupResponse(groupId);
    }
}
