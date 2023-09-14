package myproject.ourmemory.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUpload is a Querydsl query type for Upload
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUpload extends EntityPathBase<Upload> {

    private static final long serialVersionUID = -1091431767L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUpload upload = new QUpload("upload");

    public final StringPath fileName = createString("fileName");

    public final StringPath filePath = createString("filePath");

    public final NumberPath<Long> fileSize = createNumber("fileSize", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QPost post;

    public QUpload(String variable) {
        this(Upload.class, forVariable(variable), INITS);
    }

    public QUpload(Path<? extends Upload> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUpload(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUpload(PathMetadata metadata, PathInits inits) {
        this(Upload.class, metadata, inits);
    }

    public QUpload(Class<? extends Upload> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.post = inits.isInitialized("post") ? new QPost(forProperty("post"), inits.get("post")) : null;
    }

}

