package main.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "post_comments")
public class PostComments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private int id;

    @Column(name = "parent_id")
    private int parentId;

    @ManyToOne(cascade = CascadeType.ALL, fetch =FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable=false)
//    @Column(name = "post_id")
//    @NotNull
    private Posts posts;

    @ManyToOne(cascade = CascadeType.ALL, fetch =FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable=false)
//    @Column(name = "user_id")
//    @NotNull
    private Users users;

    @NotNull
    private Date time;

    @Column(columnDefinition = "text")
    @NotNull
    private String text;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public Posts getPostId() {
        return posts;
    }

    public void setPostId(Posts postId) {
        this.posts = postId;
    }

    public Users getUserId() {
        return users;
    }

    public void setUserId(Users userId) {
        this.users = userId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
