package myproject.ourmemory.service;

import lombok.RequiredArgsConstructor;
import myproject.ourmemory.domain.Comment;
import myproject.ourmemory.domain.Post;
import myproject.ourmemory.domain.User;
import myproject.ourmemory.dto.comment.CreateCommentRequest;
import myproject.ourmemory.dto.comment.GetByPostRequest;
import myproject.ourmemory.dto.post.CreatePostRequest;
import myproject.ourmemory.dto.post.CreatePostResponse;
import myproject.ourmemory.exception.CommentNotFound;
import myproject.ourmemory.exception.PostNotFound;
import myproject.ourmemory.exception.UserNotFound;
import myproject.ourmemory.repository.CommentRepository;
import myproject.ourmemory.repository.PostRepository;
import myproject.ourmemory.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    /**
     * 댓글 등록
     */
    @Transactional
    public Long createComment(CreateCommentRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(UserNotFound::new);
        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(PostNotFound::new);
        Comment comment = Comment.builder()
                .user(user)
                .post(post)
                .content(request.getContent())
                .build();
        commentRepository.save(comment);

        return comment.getId();
    }

    /**
     * 댓글 삭제
     */
    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFound::new);
        commentRepository.delete(comment);
    }

    /**
     * 댓글 단건 조회
     */
    public Comment getComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(CommentNotFound::new);
    }

    /**
     * 게시글별 댓글 목록 조회
     */
    public List<Comment> getCommentsByPost(GetByPostRequest request) {
        return commentRepository.getCommentsByPostPaging(request);
    }

    //게시글별 댓글 수
    public Long getCommentsCount(GetByPostRequest request) {

        return commentRepository.countCommentsByPost(request);
    }

    //게시글별 댓글 페이지 수
    public int getPages(GetByPostRequest request) {
        Long totalComments = commentRepository.countCommentsByPost(request);

        return (int) Math.ceil((double) totalComments / request.getSize());
    }


}
