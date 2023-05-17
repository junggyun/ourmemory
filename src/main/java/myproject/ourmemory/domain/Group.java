package myproject.ourmemory.domain;

import lombok.Builder;
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

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Long id;

    @OneToMany(mappedBy = "group")
    private List<UserGroup> userGroups = new ArrayList<>();

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    private String name;

    private String key;

    private LocalDateTime createdDate;

    public Group() {
    }

    //==생성 메서드==//
    @Builder
    public Group(String name) {
        this.name = name;
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
