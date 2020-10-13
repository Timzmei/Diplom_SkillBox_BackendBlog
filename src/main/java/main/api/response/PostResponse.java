package main.api.response;

import main.model.Post;
import org.springframework.stereotype.Component;

import java.beans.Expression;
import java.util.List;


public class PostResponse {
    private long id;
    private int isActive;
    private long moderatorId;
    private String text;
    private long timestamp;
    private String title;
    private UserPostResponse user;
    private long viewCount;

    public PostResponse(Post post) {
        this.id = post.getId();
        this.isActive = post.getIsActive();
        this.moderatorId = post.getModeratorId().getId();
        this.text = post.getText();
        this.timestamp = post.getTime().getTime();
        this.title = post.getTitle();
        this.user = new UserPostResponse(post.getUserId());
        this.viewCount = post.getViewCount();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public long getModeratorId() {
        return moderatorId;
    }

    public void setModeratorId(long moderatorId) {
        this.moderatorId = moderatorId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public UserPostResponse getUser() {
        return user;
    }

    public void setUser(UserPostResponse user) {
        this.user = user;
    }

    public long getViewCount() {
        return viewCount;
    }

    public void setViewCount(long viewCount) {
        this.viewCount = viewCount;
    }
}
