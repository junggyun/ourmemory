package myproject.ourmemory.domain;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.domain.Persistable;

import jakarta.persistence.*;

@Entity
@Getter @Setter
@Table(name = "user_group",
        uniqueConstraints = {
        @UniqueConstraint(
                name = "UserGroupConstraint",
                columnNames = {"user_id", "group_id"}
        )
        })
public class UserGroup extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_group_id")
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    @NotNull
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

    //==변경 메서드==//
    public void updateRole(UserGroup memberUser) {
        role = UserGroupRole.MEMBER;
        memberUser.role = UserGroupRole.HOST;
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
