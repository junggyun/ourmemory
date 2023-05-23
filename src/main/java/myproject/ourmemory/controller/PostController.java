package myproject.ourmemory.controller;

import lombok.RequiredArgsConstructor;
import myproject.ourmemory.domain.Post;
import myproject.ourmemory.dto.post.CreatePostRequest;
import myproject.ourmemory.dto.post.CreatePostResponse;
import myproject.ourmemory.dto.post.PostDto;
import myproject.ourmemory.repository.GroupRepository;
import myproject.ourmemory.repository.UserRepository;
import myproject.ourmemory.service.PostService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final PostService postService;

    @PostMapping("/posts")
    public CreatePostResponse createPost(@Valid @RequestBody CreatePostRequest request) {

        Long postId = postService.createPost(request);

        return new CreatePostResponse(postId);
    }

    @GetMapping("/posts/{postId}")
    public PostDto user(@PathVariable Long postId) {
        Post post = postService.findOnePost(postId);

        return new PostDto(post);
    }
}
