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
    @Column(unique = true)
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String name;

    @NotNull
    @Column(unique = true)
    private String nickName;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToMany(mappedBy = "user")
    private List<UserGroup> userGroups = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();

    public User() {
    }

    //==생성 메서드==//
    @Builder
    public User(String email, String password, String name, String nickName) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickName = nickName;
        if (nickName.equals("관리자")) {
            role = UserRole.ADMIN;
        } else {
            role = UserRole.USER;
        }
    }

    //==변경 메서드==//
    public void updateUser(UpdateUserRequest request) {
        nickName = request.getNickName();
    }

}
