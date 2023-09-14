package myproject.ourmemory.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import myproject.ourmemory.domain.Post;
import myproject.ourmemory.dto.post.*;
import myproject.ourmemory.repository.GroupRepository;
import myproject.ourmemory.service.PostService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {

    private final GroupRepository groupRepository;
    private final PostService postService;
    private final ObjectMapper objectMapper;

    /**
     * 게시글 단건 조회
     */
    @GetMapping("/posts/{postId}")
    public PostDto post(@PathVariable Long postId) {
        Post post = postService.findOnePost(postId);

        return new PostDto(post);
    }

    /**
     * 게시글 페이징 조회
     */
    @GetMapping("/posts")
    public List<PostDto> posts(@ModelAttribute GetPostRequest request) {
        List<Post> posts = postService.findPosts(request);
        List<PostDto> result = posts.stream()
                .map(p -> new PostDto(p))
                .collect(Collectors.toList());

        return result;
    }

    /**
     * 그룹별 게시글 페이징 조회
     */
    @GetMapping("/posts/byGroup")
    public GetPostByGroupResponse postsByGroup(@ModelAttribute GetPostRequest request) {
        List<Post> posts = postService.findPostsByGroup(request);
        int totalPages = postService.getPages(request);

        List<PostDto> collect = posts.stream()
                .map(p -> new PostDto(p))
                .collect(Collectors.toList());

        GetPostByGroupResponse result = GetPostByGroupResponse.builder()
                .totalPages(totalPages)
                .groupId(request.getGroupId())
                .posts(collect)
                .build();


        return result;
    }

    /**
     * 게시글 등록
     */
    @PostMapping(value = "/posts", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public CreatePostResponse createPost(
            @RequestPart(name = "request") CreatePostRequest request,
            @RequestPart(name = "files", required = false) List<MultipartFile> files) throws IOException {

        Long postId = postService.createPost(request, files);

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

    /**
     * 게시글 조회 수 증가
     */
    @PostMapping("/posts/{postId}/view")
    public void addViewCount(@PathVariable Long postId) {
        postService.addViewCount(postId);

    }
}
