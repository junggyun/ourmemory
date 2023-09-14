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
    private String key;
    private String createdDate;
    private int postCount;
    private String newPostDate;


    public GroupDto(Group group) {
        id = group.getId();
        name = group.getName();
        key = group.getKey();
        createdDate = group.getCreatedDate();
        postCount = group.getPosts().size();
        if (postCount > 0) {
            newPostDate = group.getPosts().get(postCount-1).getCreatedDate();
        } else {
            newPostDate = "";
        }
    }

}
