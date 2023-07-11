package myproject.ourmemory.dto.post;

import lombok.Getter;
import myproject.ourmemory.domain.Group;
import myproject.ourmemory.domain.Post;
import myproject.ourmemory.domain.User;
import myproject.ourmemory.dto.file.UploadDto;
import myproject.ourmemory.dto.group.GroupDto;
import myproject.ourmemory.dto.user.UserDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostDto {

    private Long postId;
    private UserDto user;
    private String title;
    private String content;
    private String createdDate;
    private String modifiedDate;
    private Long viewCount;
    private List<UploadDto> uploads;

    public PostDto() {
    }

    public PostDto(Post post) {
        postId = post.getId();
        user = new UserDto(post.getUser());
        title = post.getTitle();
        content = post.getContent();
        createdDate = post.getCreatedDate();
        modifiedDate = post.getModifiedDate();
        viewCount = post.getViewCount();
        if (post.getUploads() != null) {
            uploads = post.getUploads().stream()
                    .map(u -> new UploadDto(u))
                    .collect(Collectors.toList());
        }

    }
}
