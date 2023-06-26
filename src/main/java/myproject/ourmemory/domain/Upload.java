package myproject.ourmemory.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "upload")
public class Upload {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "upload_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private String fileName;

    private String filePath;

    private Long fileSize;

    public Upload() {
    }

    @Builder
    public Upload(Post post, String fileName, String filePath, Long fileSize) {
        this.post = post;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
    }

}
