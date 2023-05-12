package myproject.ourmemory.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Table(name = "post")
public class Post {

    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    private String title;

    private String content;

    private LocalDateTime regDate;

    //==생성 메서드==//
    public static Post createPost(User user, Group group, String title, String content) {
        Post post = new Post();
        post.setUser(user);
        post.setGroup(group);
        post.setTitle(title);
        post.setContent(content);
        post.setRegDate(LocalDateTime.now());

        return post;
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
