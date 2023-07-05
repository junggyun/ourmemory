package myproject.ourmemory.service;

import com.querydsl.core.util.CollectionUtils;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import myproject.ourmemory.domain.Group;
import myproject.ourmemory.domain.Post;
import myproject.ourmemory.domain.Upload;
import myproject.ourmemory.domain.User;
import myproject.ourmemory.dto.post.CreatePostRequest;
import myproject.ourmemory.dto.post.GetPostRequest;
import myproject.ourmemory.dto.post.UpdatePostRequest;
import myproject.ourmemory.dto.post.UpdatePostResponse;
import myproject.ourmemory.exception.GroupNotFound;
import myproject.ourmemory.exception.PostNotFound;
import myproject.ourmemory.exception.UserNotFound;
import myproject.ourmemory.repository.*;
import myproject.ourmemory.repository.GroupRepository;
import myproject.ourmemory.repository.PostRepository;
import myproject.ourmemory.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final PostRepository postRepository;

    /**
     * 게시글 등록
     */
    @Transactional
    public Long createPost(CreatePostRequest request, List<MultipartFile> files) throws IOException {
        List<Upload> uploads = new ArrayList<>();

        if (files != null) {
            for (MultipartFile file : files) {
                String uploadPath = System.getProperty("user.dir") + "\\front\\public\\image";
                String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                File saveFile = new File(uploadPath, fileName);
                file.transferTo(saveFile);

                Upload upload = Upload.builder()
                        .fileName(fileName)
                        .filePath("/image/" + fileName)
                        .fileSize(file.getSize())
                        .build();
                uploads.add(upload);
            }
        }

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(UserNotFound::new);
        Group group = groupRepository.findById(request.getGroupId())
                .orElseThrow(GroupNotFound::new);

        Post post = Post.builder()
                .user(user)
                .group(group)
                .title(request.getTitle())
                .content(request.getContent())
                .uploads(uploads)
                .build();

        postRepository.save(post);

        return post.getId();
    }

    /**
     * 게시글 수정
     */
    @Transactional
    public void updatePost(Long postId, UpdatePostRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFound::new);
        post.updatePost(request);

    }

    /**
     * 게시글 삭제
     */
    @Transactional
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFound::new);
        List<Upload> uploads = post.getUploads();
        if (uploads != null) {
            for (Upload upload : uploads) {
                File file = new File(System.getProperty("user.dir") + "\\front\\public\\image\\" + upload.getFileName());
                file.delete();
            }
        }


        postRepository.delete(post);
    }

    /**
     * 게시글 조회
     */
    //특정 게시글 조회
    public Post findOnePost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(PostNotFound::new);
    }

    //전체 게시글 조회
    public List<Post> findAllPosts() {
        return postRepository.findAll();
    }

    //전체 게시글 페이징 조회
    public List<Post> findPosts(GetPostRequest request) {
        return postRepository.findPosts(request);
    }

    //그룹별 게시글 페이징 조회
    public List<Post> findPostsByGroup(GetPostRequest request) {
        return postRepository.findPostsByGroup(request);
    }

    //그룹별 게시글 페이지 수
    public int getPages(GetPostRequest request) {
        Long totalPosts = postRepository.countPostsByGroup(request);
        int totalPages = (int) Math.ceil((double) totalPosts / request.getSize());

        return totalPages;
    }


}
