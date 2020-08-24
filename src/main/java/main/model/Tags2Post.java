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
    private Post post;

    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST
    )
    @JoinColumn(name = "tag_id", nullable=false)
//    @Column(name = "tag_id")
//    @NotNull
    private Tag tag;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Post getPostId() {
        return post;
    }

    public void setPostId(Post postId) {
        this.post = postId;
    }

    public Tag getTagId() {
        return tag;
    }

    public void setTagId(Tag tagId) {
        this.tag = tagId;
    }
}
