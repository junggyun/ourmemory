package myproject.ourmemory.controller;

import lombok.RequiredArgsConstructor;
import myproject.ourmemory.domain.Comment;
import myproject.ourmemory.dto.comment.*;
import myproject.ourmemory.service.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    /**
     * 댓글 등록
     */
    @PostMapping("/comments")
    public CreateCommentResponse createComment(@RequestBody CreateCommentRequest request) {
        Long commentId = commentService.createComment(request);

        return new CreateCommentResponse(commentId);
    }

    /**
     * 댓글 삭제
     */
    @DeleteMapping("/comments/{commentId}")
    public void deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);

    }

    /**
     * 댓글 단건 조회
     */
    @GetMapping("/comments/{commentId}")
    public CommentDto comment(@PathVariable Long commentId) {
        Comment comment = commentService.getComment(commentId);

        return new CommentDto(comment);
    }

    /**
     * 게시글별 댓글 목록 조회
     */
    @GetMapping("/comments/byPost")
    public GetByPostResponse commentsByPost(GetByPostRequest request) {
        int totalPages = commentService.getPages(request);
        Long totalCount = commentService.getCommentsCount(request);
        List<Comment> findComments = commentService.getCommentsByPost(request);
        List<CommentDto> collect = findComments.stream()
                .map(CommentDto::new)
                .collect(Collectors.toList());

        return GetByPostResponse.builder()
                .postId(request.getPostId())
                .totalPages(totalPages)
                .totalCount(totalCount)
                .comments(collect)
                .build();
    }
}
