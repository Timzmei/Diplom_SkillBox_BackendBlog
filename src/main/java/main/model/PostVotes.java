package main.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "post_votes")
public class PostVotes
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private int id;

    @ManyToOne(cascade = CascadeType.ALL, fetch =FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable=false)
//    @Column(name = "user_id")
//    @NotNull
    private Users  users;

    @ManyToOne(cascade = CascadeType.ALL, fetch =FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable=false)
//    @Column(name = "post_id")
//    @NotNull
    private Posts posts;

    @NotNull
    private Date time;

    @Column(name = "value", columnDefinition = "tinyint(1)")
    @NotNull
    private byte value;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Users getUserId() {
        return users;
    }

    public void setUserId(Users userId) {
        this.users = userId;
    }

    public Posts getPostId() {
        return posts;
    }

    public void setPostId(Posts postId) {
        this.posts = postId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public byte getValue() {
        return value;
    }

    public void setValue(byte value) {
        this.value = value;
    }
}
