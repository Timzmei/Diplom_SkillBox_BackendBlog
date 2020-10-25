package main.service;

import main.api.response.PostsResponse;
import main.model.Post;
import main.model.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // В процессе
public class CalendarService {

    @Autowired
    private PostRepository postRepository;

    public PostsResponse getPostsSearch(String year) {

        List<Post> pageOnDate = postRepository.findAllPostsOnDate(year);

        return null;
    }
}
