package main.api.response;

import main.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class ApiPostResponse {


    private long count;
    private List<PostResponse> posts;



    public long getCount() {
        return count;
    }

    public void setCount(Page page) {
        this.count = page.getTotalElements();
    }

    public List<PostResponse> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> post) {

        List<PostResponse> listPostResponses = new LinkedList<>();

        for (Post p:post) {
            listPostResponses.add(new PostResponse(p));
        }
        this.posts.addAll(listPostResponses);
    }
}
