package myproject.ourmemory.repository;

import myproject.ourmemory.domain.Upload;

import java.util.List;

public interface CustomUploadRepository {

    List<Upload> findByPost(Long postId);
}
