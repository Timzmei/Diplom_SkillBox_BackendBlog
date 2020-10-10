package main.service;

import main.api.response.ApiPostResponse;
import main.api.response.PostResponse;
import main.model.Post;
import main.model.repo.PostsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PostService implements IPostService {

    @Autowired
    private PostsRepo repository;

    @Override
    public ApiPostResponse findPaginated(int pageNo, int pageSize) {
//        PostResponse postResponse = new PostResponse();
        ApiPostResponse apiPostResponse = new ApiPostResponse();

        Iterable<Post> pages = repository.findAll();

        Page<Post> page = repository.findAll(pageable);


        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<Post> pagedResult = (Page<Post>) repository.findAll();

        apiPostResponse.setCount(pagedResult);
        apiPostResponse.setPosts(page.getContent());

        return apiPostResponse;
    }
}
