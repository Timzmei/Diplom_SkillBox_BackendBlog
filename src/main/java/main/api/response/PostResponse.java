package main.api.response;

import main.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.Expression;
import java.util.List;


public class PostResponse {
    private int id;
    private long timestamp;
    private UserResponse user;
    private String title;
    private String announce;
    private int likeCount;
    private int dislikeCount;
    private int viewCount;



    public PostResponse(Post post) {
        this.id = post.getId();
        this.timestamp = post.getTime().getTime();
        this.user = new UserResponse(post);
        this.title = post.getTitle();
        this.announce = post.getText().substring(0, 50);
        this.likeCount = setLikeCount(post);
        this.dislikeCount = setDislikeCount(post);
        this.viewCount = post.getViewCount();
    }

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

    public int setLikeCount(Post post) {
        int count = 0;
        List<Byte> like = post.getLike();
        if(!(like == null)) {
            for (int i : like) {
                if (i == 1)
                    count += 1;
            }
        }
        return count;
    }

    public int getDislikeCount() {
        return dislikeCount;
    }

    public int setDislikeCount(Post post) {
        int count = 0;
        List<Byte> like = post.getLike();
        if(!(like == null)) {

            for (int i : like) {
                if (i == -1)
                    count += 1;
            }
        }
        return count;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(Post post) {
        this.viewCount = post.getViewCount();
    }
}
