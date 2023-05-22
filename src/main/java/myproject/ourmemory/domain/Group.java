package myproject.ourmemory.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import myproject.ourmemory.dto.group.UpdateGroupRequest;
import org.apache.commons.lang3.RandomStringUtils;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDateTime.now;

@Entity
@Getter @Setter
@Table(name = "groups")
public class Group {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Long id;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<UserGroup> userGroups = new ArrayList<>();

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    private String name;

    private String key;


    public Group() {
    }

    //==생성 메서드==//
    @Builder
    public Group(String name) {
        this.name = name;
        setKey();
    }


    //==변경 메서드==//
    public void updateName(UpdateGroupRequest request) {
        name = request.getName();
    }

    //==비즈니스 메서드==//
    public void setKey() {
        key = RandomStringUtils.randomNumeric(8);
    }


}
