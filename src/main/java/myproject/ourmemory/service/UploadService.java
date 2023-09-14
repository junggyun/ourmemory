package myproject.ourmemory.service;

import lombok.RequiredArgsConstructor;
import myproject.ourmemory.domain.Upload;
import myproject.ourmemory.dto.file.UploadDto;
import myproject.ourmemory.repository.UploadRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UploadService {
    private final UploadRepository uploadRepository;

    /**
     * 게시글 별 이미지 조회
     */
    public List<Upload> findUploads(Long postId) {

        return uploadRepository.findByPost(postId);
    }
}
