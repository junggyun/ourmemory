package myproject.ourmemory.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -1297221005L;

    public static final QUser user = new QUser("user");

    public final QBaseTimeEntity _super = new QBaseTimeEntity(this);

    //inherited
    public final StringPath createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final StringPath modifiedDate = _super.modifiedDate;

    public final StringPath name = createString("name");

    public final StringPath nickName = createString("nickName");

    public final ListPath<Post, QPost> posts = this.<Post, QPost>createList("posts", Post.class, QPost.class, PathInits.DIRECT2);

    public final ListPath<UserGroup, QUserGroup> userGroups = this.<UserGroup, QUserGroup>createList("userGroups", UserGroup.class, QUserGroup.class, PathInits.DIRECT2);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

