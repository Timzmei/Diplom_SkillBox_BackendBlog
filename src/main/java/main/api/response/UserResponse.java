package main.api.response;

import main.model.Post;
import main.model.User;
import org.springframework.stereotype.Component;

public class UserResponse {

    private final int id;
    private final String name;


    public UserResponse(Post post) {
        id = post.getUser().getId();
        name = post.getUser().getName();
    }

}
