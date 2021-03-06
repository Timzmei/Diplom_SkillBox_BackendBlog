package main.api.response;

import main.model.Post;
import main.model.PostVotes;

import java.util.LinkedList;
import java.util.List;


public class PostResponse {
    private String text;
    private long id;
    private long timestamp;
    private UserPostResponse user;
    private String title;
    private long likeCount;
    private long dislikeCount;
    private long viewCount;
    private boolean active;
    private List<CommentResponse> comments;
    private List<String> tags;




    public PostResponse(List<CommentResponse> comments, Post post, List<String> tags) {
        this.id = post.getId();
        this.timestamp = post.getTime().getTime() / 1000;
        this.active = setActive(post.getIsActive());
        this.user = new UserPostResponse(post);
        this.title = post.getTitle();
        this.text = post.getText();
        this.likeCount = getLikeCount(post);
        this.dislikeCount = getDislikeCount(post);
        this.viewCount = post.getViewCount();
        this.comments = comments;
        this.tags = tags;
    }

    public long getLikeCount() {
        return likeCount;
    }

    public long getDislikeCount() {
        return dislikeCount;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean setActive(byte a) {
        this.active = a == 1;

        return this.active;
    }

    public String setAnnounce(Post post) {

        String announce = post.getText()
                .replaceAll("</div>", " ")
                .replaceAll("\\<.*?\\>", "")
                .replaceAll("&nbsp;", " ");

        if (announce.length() > 400){
            return announce.substring(0, 400) + "...";
        }
        return announce;
    }

    public long getLikeCount(Post post) {
        likeCount = 0;

        if(!(post.getLike() == null)) {
            LinkedList<PostVotes> like = new LinkedList<>(post.getLike());
            for (PostVotes l : like
            ) {
                if (l.getValue() == 1) {
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

            LinkedList<PostVotes> like = new LinkedList<>(post.getLike());
            for (PostVotes l : like
            ) {
                if (l.getValue() == 0) {
                    dislikeCount++;
                }

            }
        }
        return dislikeCount;
    }

    public void setDislikeCount(long dislikeCount) {
        this.dislikeCount = dislikeCount;
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

    public List<CommentResponse> getComments() {
        return comments;
    }

    public void setComments(List<CommentResponse> comments) {
        this.comments = comments;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
