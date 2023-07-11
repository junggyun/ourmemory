package myproject.ourmemory.dto.comment;

import lombok.Builder;
import lombok.Getter;
import myproject.ourmemory.domain.Comment;
import myproject.ourmemory.dto.user.UserDto;

import java.util.Date;

@Getter
public class CommentDto {

    private final Long commentId;
    private final Long postId;
    private final String userNickName;
    private final String content;
    private final String createdDate;

    public CommentDto(Comment comment) {
        commentId = comment.getId();
        postId = comment.getPost().getId();
        userNickName = comment.getUser().getNickName();
        content = comment.getContent();
        createdDate = comment.getCreatedDate();
    }
}
