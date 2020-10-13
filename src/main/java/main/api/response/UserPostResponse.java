package main.api.response;

import main.model.User;
import org.springframework.stereotype.Component;

public class UserPostResponse {

    private int id;
    private String name;

    public UserPostResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
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
