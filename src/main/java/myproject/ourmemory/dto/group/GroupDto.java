package myproject.ourmemory.dto.group;

import lombok.Getter;
import myproject.ourmemory.domain.Group;
import myproject.ourmemory.dto.post.PostDto;
import myproject.ourmemory.dto.usergroup.UserList;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class GroupDto {

    private Long id;
    private String name;
    private String createdDate;


    public GroupDto(Group group) {
        id = group.getId();
        name = group.getName();
        createdDate = group.getCreatedDate();
    }

}
