package main.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Posts
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

    @Column(name = "moderator_id", columnDefinition = "integer")
    private int moderatorId;

    @ManyToOne(cascade = CascadeType.ALL, fetch =FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable=false)
//    @Column(name = "user_id")
//    @NotNull
    private Users users;

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


    public int getId() {
        return id;
    }

    public byte getIsActive() {
        return isActive;
    }

    public void setIsActive(byte isActive) {
        this.isActive = isActive;
    }

    public ModerationStatus getModerationStatus() {
        return moderationStatus;
    }

    public void setModerationStatus(ModerationStatus moderationStatus) {
        this.moderationStatus = moderationStatus;
    }

    public int getModeratorId() {
        return moderatorId;
    }

    public void setModeratorId(int moderatorId) {
        this.moderatorId = moderatorId;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }
}
