package myproject.ourmemory.controller;

import lombok.RequiredArgsConstructor;
import myproject.ourmemory.domain.Post;
import myproject.ourmemory.dto.post.*;
import myproject.ourmemory.repository.GroupRepository;
import myproject.ourmemory.repository.UserRepository;
import myproject.ourmemory.service.PostService;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {

    private final GroupRepository groupRepository;
    private final PostService postService;

    /**
     * 게시글 단건 조회
     */
    @GetMapping("/posts/{postId}")
    public PostDto user(@PathVariable Long postId) {
        Post post = postService.findOnePost(postId);

        return new PostDto(post);
    }

    /**
     * 게시글 페이징 조회
     */
    @GetMapping("/posts")
    public List<PostDto> users(@ModelAttribute GetPostRequest request) {
        List<Post> posts = postService.findPosts(request);
        List<PostDto> result = posts.stream()
                .map(p -> new PostDto(p))
                .collect(Collectors.toList());

        return result;
    }

    /**
     * 게시글 등록
     */
    @PostMapping("/posts")
    public CreatePostResponse createPost(@Valid @RequestBody CreatePostRequest request) {

        Long postId = postService.createPost(request);

        return new CreatePostResponse(postId);
    }

    /**
     * 게시글 수정
     */
    @PostMapping("/posts/{postId}")
    public UpdatePostResponse updatePost(@PathVariable Long postId, @Valid @RequestBody UpdatePostRequest request) {
        postService.updatePost(postId, request);

        return new UpdatePostResponse(postId);
    }

    /**
     * 게시글 삭제
     */
    @DeleteMapping("/posts/{postId}")
    public DeletePostResponse deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);

        return new DeletePostResponse(postId);
    }
}
