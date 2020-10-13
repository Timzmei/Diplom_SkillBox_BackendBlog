package main.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

import java.util.List;


public class PostsResponse {

    @JsonProperty("count")
    private int count;

    @JsonProperty("posts")
    private List<PostResponse> posts;

//    public PostsResponse(int count, List<PostResponse> posts) {
//        this.count = count;
//        this.posts = posts;
//    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<PostResponse> getPosts() {
        return posts;
    }

    public void setPosts(List<PostResponse> posts) {
        this.posts = posts;
    }
}
