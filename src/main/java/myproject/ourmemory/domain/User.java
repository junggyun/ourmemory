package myproject.ourmemory.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "user")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @OneToMany(mappedBy = "user")
    private List<UserGroup> userGroups = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    private String name;

    private String nickName;

    public User() {
    }

    //==생성 메서드==//
    @Builder
    public User(String name, String nickName) {
        this.name = name;
        this.nickName = nickName;
    }

    //==변경 메서드==//
    public void updateNickName(String nickName) {
        this.nickName = nickName;
    }

    //==연관관계 메서드==//
    public void setUserGroup(UserGroup userGroup) {
        userGroups.add(userGroup);
        userGroup.setUser(this);
    }

}
