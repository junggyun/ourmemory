package myproject.ourmemory.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import myproject.ourmemory.dto.post.UpdatePostRequest;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDateTime.*;

@Entity
@Getter @Setter
@Table(name = "post")
public class Post extends BaseTimeEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
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
    private String title;

    @NotNull
    @Lob
    private String content;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true) //todo CascadeType.ALL 을 안할 시 업로드 안되는 이유??
    private List<Upload> uploads = new ArrayList<>();


    public Post() {
    }

    //==생성 메서드==//
    @Builder
    public Post(User user, Group group, String title, String content, List<Upload> uploads) {
        setUser(user);
        setGroup(group);
        setUpload(uploads);
        this.title = title;
        this.content = content;
    }

    //==변경 메서드==//
    public void updatePost(UpdatePostRequest request) {
        title = request.getTitle();
        content = request.getContent();
    }

    //==연관관계 메서드==//
    public void setUser(User user) {
        this.user = user;
        user.getPosts().add(this);
    }

    public void setGroup(Group group) {
        this.group = group;
        group.getPosts().add(this);
    }

    public void setUpload(List<Upload> uploads) {
        this.uploads = uploads;
        for (Upload upload : uploads) {
            upload.setPost(this);
        }
    }
}
