package myproject.ourmemory.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDateTime.now;

@Entity
@Getter @Setter
@Table(name = "group")
public class Group {

    @Id @GeneratedValue
    @Column(name = "group_id")
    private Long id;

    @OneToMany(mappedBy = "group")
    private List<UserGroup> userGroups = new ArrayList<>();

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    private String name;

    private String key;

    private LocalDateTime regDate;

    //==생성 메서드==//
    public static Group createGroup(String name) {
        Group group = new Group();
        group.setName(name);
        group.setRegDate(now());

        return group;
    }

    //==변경 메서드==//
    public void updateName(String name) {
        this.name = name;
    }

    //==연관관계 메서드==//
    public void setUserGroup(UserGroup userGroup) {
        userGroups.add(userGroup);
        userGroup.setGroup(this);
    }

}
