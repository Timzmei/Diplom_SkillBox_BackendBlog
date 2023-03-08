package main.api.response;

import main.model.Post;
import main.model.User;

public class UserPostResponse {

    private int id;
    private String name;
    private String photo;


    public UserPostResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
    }

    public UserPostResponse(Post post) {
        this.id = post.getUser().getId();
        this.name = post.getUser().getName();
        this.photo = post.getUser().getPhoto();
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
