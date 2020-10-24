package main.api.response;

import main.model.Post;
import org.springframework.stereotype.Component;

import java.beans.Expression;
import java.util.LinkedList;
import java.util.List;


public class PostResponse {
    private long id;
    private long timestamp;
    private UserPostResponse user;
    private String title;
    private String announce;
    private long likeCount;
    private long dislikeCount;
    private long commentCount;
    private long viewCount;

    public PostResponse(Post post) {
        this.id = post.getId();
        this.timestamp = post.getTime().getTime() / 1000;
        this.user = new UserPostResponse(post.getUserId());
        this.title = post.getTitle();
        this.announce = post.getText();
        this.likeCount = getLikeCount(post);
        this.likeCount = getDislikeCount(post);

        this.commentCount = setCommentCount(post);
        this.viewCount = post.getViewCount();
    }

    public String getAnnounce() {
        return announce;
    }

    public void setAnnounce(String announce) {
        this.announce = announce;
    }

    public long getLikeCount(Post post) {
        likeCount = 0;

        if(!(post.getLike() == null)) {
            LinkedList<Byte> like = new LinkedList<>();
            like.addAll(post.getLike());
            for (Byte l : like
            ) {
                if (l == 1) {
                    likeCount++;
                }

            }
        }

        return likeCount;
    }

    public void setLikeCount(long likeCount) {
        this.likeCount = likeCount;
    }

    public long getDislikeCount(Post post) {
        dislikeCount = 0;

        if(!(post.getLike() == null)) {

            LinkedList<Byte> like = new LinkedList<>();
            like.addAll(post.getLike());
            for (Byte l : like
            ) {
                if (l == 0) {
                    dislikeCount++;
                }

            }
        }
        return dislikeCount;
    }

    public void setDislikeCount(long dislikeCount) {
        this.dislikeCount = dislikeCount;
    }

    public long getCommentCount() {
        return commentCount;
    }

    public long setCommentCount(Post post) {

        if(!(post.getComments() == null)) {
            commentCount = post.getComments().size();
        }
        else {
            commentCount = 0;
        }

        return commentCount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
