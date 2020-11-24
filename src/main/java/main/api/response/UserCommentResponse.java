package main.api.response;

import main.model.User;

public class UserCommentResponse {
    private int id;
    private String name;
    private String photo;

    public UserCommentResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.photo = user.getPhoto();
    }

    public String getPhoto() {
        return photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
