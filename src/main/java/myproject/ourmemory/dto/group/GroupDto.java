package myproject.ourmemory.dto.group;

import lombok.Getter;
import myproject.ourmemory.domain.Group;

@Getter
public class GroupDto {

    private Long id;
    private String name;

    public GroupDto(Group group) {
        id = group.getId();
        name = group.getName();
    }
}
