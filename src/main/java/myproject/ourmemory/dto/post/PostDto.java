package myproject.ourmemory.dto.post;

import lombok.Getter;
import myproject.ourmemory.domain.Group;
import myproject.ourmemory.domain.Post;
import myproject.ourmemory.domain.User;
import myproject.ourmemory.dto.group.GroupDto;
import myproject.ourmemory.dto.user.UserDto;

import java.time.LocalDateTime;

@Getter
public class PostDto {

    private Long postId;
    private UserDto user;
    private GroupDto group;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public PostDto() {
    }

    public PostDto(Post post) {
        postId = post.getId();
        user = new UserDto(post.getUser());
        group = new GroupDto(post.getGroup());
        title = post.getTitle();
        content = post.getContent();
        createdDate = post.getCreatedDate();
        modifiedDate = post.getModifiedDate();
    }
}
