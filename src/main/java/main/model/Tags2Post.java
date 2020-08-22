package main.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Tags2Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private int id;


    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST
    )
    @JoinColumn(name = "post_id", nullable=false)
//    @Column(name = "post_id")
//    @NotNull
    private Posts posts;

    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST
    )
    @JoinColumn(name = "tag_id", nullable=false)
//    @Column(name = "tag_id")
//    @NotNull
    private Tags tags;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Posts getPostId() {
        return posts;
    }

    public void setPostId(Posts postId) {
        this.posts = postId;
    }

    public Tags getTagId() {
        return tags;
    }

    public void setTagId(Tags tagId) {
        this.tags = tagId;
    }
}
