package main.api.response;

import main.model.PostComments;

public class CommentResponse {


    private final int id;
    private final long timestamp;
    private final String text;
    private final UserCommentResponse user;

    public CommentResponse(PostComments postComments) {
        this.id = postComments.getId();
        this.timestamp = postComments.getTime().getTime() / 1000;
        this.text = postComments.getText();
        this.user = new UserCommentResponse(postComments.getUser());
    }

    public int getId() {
        return id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getText() {
        return text;
    }

    public UserCommentResponse getUser() {
        return user;
    }
}
