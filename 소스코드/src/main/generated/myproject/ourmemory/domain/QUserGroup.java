package myproject.ourmemory.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserGroup is a Querydsl query type for UserGroup
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserGroup extends EntityPathBase<UserGroup> {

    private static final long serialVersionUID = 1431935948L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserGroup userGroup = new QUserGroup("userGroup");

    public final QBaseTimeEntity _super = new QBaseTimeEntity(this);

    //inherited
    public final StringPath createdDate = _super.createdDate;

    public final QGroup group;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final StringPath modifiedDate = _super.modifiedDate;

    public final EnumPath<UserGroupRole> role = createEnum("role", UserGroupRole.class);

    public final QUser user;

    public QUserGroup(String variable) {
        this(UserGroup.class, forVariable(variable), INITS);
    }

    public QUserGroup(Path<? extends UserGroup> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserGroup(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserGroup(PathMetadata metadata, PathInits inits) {
        this(UserGroup.class, metadata, inits);
    }

    public QUserGroup(Class<? extends UserGroup> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.group = inits.isInitialized("group") ? new QGroup(forProperty("group")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

