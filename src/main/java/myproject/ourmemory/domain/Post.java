package myproject.ourmemory.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.*;

@Entity
@Getter @Setter
@Table(name = "post")
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    private String title;

    @Lob
    private String content;

    private LocalDateTime createdDate;

    public Post() {
    }

    //==생성 메서드==//
    @Builder
    public Post(User user, Group group, String title, String content) {
        this.user = user;
        this.group = group;
        this.title = title;
        this.content = content;
        this.createdDate = now();
    }

    //==변경 메서드==//

    //==연관관계 메서드==//
    public void setUser(User user) {
        this.user = user;
        user.getPosts().add(this);
    }

    public void setGroup(Group group) {
        this.group = group;
        group.getPosts().add(this);
    }
}
