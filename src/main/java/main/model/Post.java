package main.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Post
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private int id;

    @Column(name = "is_active", columnDefinition = "tinyint(1)")
    @NotNull
    private byte isActive;

    @Enumerated(EnumType.STRING)
    @Column(name = "moderation_status", columnDefinition = "enum('NEW','ACCEPTED','DECLINED') default 'NEW'")
    @NotNull
    private ModerationStatus moderationStatus;

    @ManyToOne(cascade = CascadeType.ALL, fetch =FetchType.LAZY)
    @JoinColumn(name = "moderator_id")
//    @Column(name = "moderator_id", columnDefinition = "integer")
    private User moderator;

    @ManyToOne(cascade = CascadeType.ALL, fetch =FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable=false)
//    @Column(name = "user_id")
//    @NotNull
    private User user;

    @NotNull
    private Date time;

    @Column(columnDefinition = "varchar(255)")
    @NotNull
    private String title;

    @Column(columnDefinition = "text")
    @NotNull
    private String text;

    @Column(name = "view_count", columnDefinition = "int")
    @NotNull
    private int viewCount;

////    @Transient
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinTable(name = "Tags2Post",
//            joinColumns = {@JoinColumn(name = "post_id")},
//            inverseJoinColumns = {@JoinColumn(name = "tag_id")}
//    )
//    transient List<Tag> tags;

    @OneToMany(mappedBy="post")
    private List<Tags2Post> tags;

    @OneToMany(mappedBy="post")
    private List<PostVotes> like;

    @OneToMany(mappedBy="post")
    private List<PostComments> comments;

}
