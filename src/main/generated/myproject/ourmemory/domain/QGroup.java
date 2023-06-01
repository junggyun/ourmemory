package myproject.ourmemory.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGroup is a Querydsl query type for Group
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGroup extends EntityPathBase<Group> {

    private static final long serialVersionUID = -1572094761L;

    public static final QGroup group = new QGroup("group1");

    public final QBaseTimeEntity _super = new QBaseTimeEntity(this);

    //inherited
    public final StringPath createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath key = createString("key");

    //inherited
    public final StringPath modifiedDate = _super.modifiedDate;

    public final StringPath name = createString("name");

    public final ListPath<Post, QPost> posts = this.<Post, QPost>createList("posts", Post.class, QPost.class, PathInits.DIRECT2);

    public final ListPath<UserGroup, QUserGroup> userGroups = this.<UserGroup, QUserGroup>createList("userGroups", UserGroup.class, QUserGroup.class, PathInits.DIRECT2);

    public QGroup(String variable) {
        super(Group.class, forVariable(variable));
    }

    public QGroup(Path<? extends Group> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGroup(PathMetadata metadata) {
        super(Group.class, metadata);
    }

}

