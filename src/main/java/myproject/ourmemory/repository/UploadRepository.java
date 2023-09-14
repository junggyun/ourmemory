package myproject.ourmemory.repository;

import myproject.ourmemory.domain.Upload;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadRepository extends JpaRepository<Upload, Long>, CustomUploadRepository {


}
