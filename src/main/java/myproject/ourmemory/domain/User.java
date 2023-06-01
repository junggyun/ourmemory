package myproject.ourmemory.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import myproject.ourmemory.dto.user.UpdateUserRequest;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "user")
public class User extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @NotNull
    private String name;

    @NotNull
    @Column(unique = true)
    private String nickName;

    @OneToMany(mappedBy = "user")
    private List<UserGroup> userGroups = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    public User() {
    }

    //==생성 메서드==//
    @Builder
    public User(String name, String nickName) {
        this.name = name;
        this.nickName = nickName;
    }

    //==변경 메서드==//
    public void updateUser(UpdateUserRequest request) {
        nickName = request.getNickName();
    }

}
