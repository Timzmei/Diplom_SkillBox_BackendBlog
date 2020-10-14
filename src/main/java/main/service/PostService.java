package main.service;

import main.api.response.PostResponse;
import main.api.response.PostsResponse;
import main.model.Post;
import main.model.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;


@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public PostsResponse getPosts(int offset, int limit, String mode) {
        // тут пишем уже выбор запроса из репозитория по mode, простые if
        // и собираем объект Pagable для пагинации, после получения данных постав


        Pageable pageable = PageRequest.of(offset, limit, Sort.by("time"));

//        @Deprecated
//        Pageable pageable = new PageRequest(offset, limit);

//        Pageable pageable = PageRequest.of(offset, limit);



        Page<Post> page = postRepository.findAll(pageable);
        List<Post> listPost = postRepository.getRecentPosts();
        List<PostResponse> postResponseList = new ArrayList<>();

        for (Post p : page) {

            postResponseList.add(new PostResponse(p));

        }



        PostsResponse postsResponse = new PostsResponse();
        postsResponse.setPosts(postResponseList);
        postsResponse.setCount(listPost.size());


        return postsResponse;
    }
}
