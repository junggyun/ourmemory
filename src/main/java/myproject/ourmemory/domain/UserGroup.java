package myproject.ourmemory.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "user_group")
public class UserGroup {

    @EmbeddedId
    private UserGroupId id = new UserGroupId();

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @MapsId("groupId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    @Enumerated(EnumType.STRING)
    private UserGroupRole role;

    public UserGroup() {
    }


    //==생성 메서드==//
    @Builder
    public UserGroup(User user, Group group, UserGroupRole role) {
        this.user = user;
        this.group = group;
        this.role = role;
    }
}
