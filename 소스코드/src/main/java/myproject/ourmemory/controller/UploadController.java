package myproject.ourmemory.controller;

import lombok.RequiredArgsConstructor;
import myproject.ourmemory.domain.Upload;
import myproject.ourmemory.dto.file.UploadDto;
import myproject.ourmemory.service.UploadService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UploadController {
    private final UploadService uploadService;

    /**
     * 게시글 별 이미지 조회
     */
    @GetMapping("/uploads/{postId}")
    public List<UploadDto> findUploads(@PathVariable Long postId) {
        List<Upload> findUploads = uploadService.findUploads(postId);

        return findUploads.stream()
                .map(UploadDto::new)
                .collect(Collectors.toList());
    }
}
