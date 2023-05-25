package myproject.ourmemory.domain;

import lombok.*;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "user_group",
        uniqueConstraints = {
        @UniqueConstraint(
                name = "UserGroupConstraint",
                columnNames = {"user_id", "group_id"}
        )
        })
public class UserGroup {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

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
        setUser(user);
        setGroup(group);
        this.role = role;
    }

    //==연관관계 메서드==//
    public void setUser(User user) {
        this.user = user;
        user.getUserGroups().add(this);
    }

    public void setGroup(Group group) {
        this.group = group;
        group.getUserGroups().add(this);
    }
}
