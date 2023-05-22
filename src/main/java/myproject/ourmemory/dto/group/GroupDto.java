package myproject.ourmemory.dto.group;

import lombok.Getter;
import myproject.ourmemory.domain.Group;
import myproject.ourmemory.dto.userGroup.UserGroupDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class GroupDto {

    private Long groupId;
    private String groupName;

    public GroupDto(Group group) {
        groupId = group.getId();
        groupName = group.getName();
    }
}
