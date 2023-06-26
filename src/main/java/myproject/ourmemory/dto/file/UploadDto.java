package myproject.ourmemory.dto.file;

import lombok.Builder;
import lombok.Getter;
import myproject.ourmemory.domain.Upload;

@Getter
public class UploadDto {

    private Long id;
    private String fileName;
    private String filePath;

    public UploadDto() {
    }

    @Builder
    public UploadDto(Upload upload) {
        id = upload.getId();
        fileName = upload.getFileName();
        filePath = upload.getFilePath();
    }
}
