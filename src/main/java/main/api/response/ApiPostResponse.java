package main.api.response;

import org.springframework.data.domain.Page;

public class ApiPostResponse {


    private long count;
    private PostResponse posts;



    public long getCount() {
        return count;
    }

    public void setCount(Page page) {
        this.count = page.getTotalElements();
    }

    public PostResponse getPosts() {
        return posts;
    }

    public void setPosts(PostResponse posts) {
        this.posts = posts;
    }
}
