package main.api.response;

import main.model.Post;
import org.springframework.stereotype.Component;

import java.beans.Expression;
import java.util.List;

@Component
public class PostResponse {
    private Post post;

    private int id;
    private long timestamp;
    private UserResponse user;
    private String title;
    private String announce;
    private int likeCount;
    private int dislikeCount;
    private int viewCount;



    public int getId() {
        return id;
    }

    public void setId(Post post) {
        this.id = post.getId();
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Post post) {
        this.timestamp = post.getTime().getTime();
    }

    public UserResponse getUser() {
        return user;
    }

    public void setUser(Post post) {
        this.user = new UserResponse(post);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(Post post) {
        this.title = post.getTitle();
    }

    public String getAnnounce() {
        return announce;
    }

    public void setAnnounce(Post post) {
        this.announce = post.getText().substring(0, 50);
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Post post) {
        int count = 0;
        List<Byte> like = post.getLike();
        for(int i : like)
        {
            if (i == 1)
                count += 1;
        }
        this.likeCount = count;
    }

    public int getDislikeCount() {
        return dislikeCount;
    }

    public void setDislikeCount(Post post) {
        int count = 0;
        List<Byte> like = post.getLike();
        for(int i : like)
        {
            if (i == -1)
                count += 1;
        }
        this.dislikeCount = count;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(Post post) {
        this.viewCount = post.getViewCount();
    }
}
