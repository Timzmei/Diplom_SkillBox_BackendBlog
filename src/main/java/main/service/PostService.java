package main.service;

import main.api.response.PostResponse;
import main.api.response.PostsResponse;
import main.model.Post;
import main.model.PostComments;
import main.model.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
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


//        mode - режим вывода (сортировка):
//        recent - сортировать по дате публикации, выводить сначала новые
//        popular - сортировать по убыванию количества комментариев
//        best - сортировать по убыванию количества лайков
//        early - сортировать по дате публикации, выводить сначала старые


        Pageable pageable;
        pageable = PageRequest.of(offset, limit);
        Page<Post> pageOfTags = postRepository.findAllOrderByTimeDesc(pageable);

        if (mode.equals("popular")) {
            pageOfTags = postRepository.findAllOrderByCommentsDesc(pageable);
        }
        else {
            if (mode.equals("best")) {
                pageOfTags = postRepository.findAllOrderByVotesDesc(pageable);
            }
            else {
                if (mode.equals("early")) {
                    pageOfTags = postRepository.findAllOrderByTime(pageable);
                }
            }
        }

        return createPostsResponse(pageOfTags, postRepository.findAllPosts().size());
    }

    public PostsResponse getPostsSearch(int offset, int limit, String query) {
        Pageable pageable;
        pageable = PageRequest.of(offset, limit);
        Page<Post> pageOfTags = postRepository.findAllOrderBySearch(query, pageable);

        return createPostsResponse(pageOfTags, (int)pageOfTags.getTotalElements());
    }













    private PostsResponse createPostsResponse(Page<Post> pageOfTags, int size){

        List<PostResponse> postResponseList = new ArrayList<>();
        for (Post p : pageOfTags) {
            postResponseList.add(new PostResponse(p));
        }
        PostsResponse postsResponse = new PostsResponse();
        postsResponse.setPosts(postResponseList);
        postsResponse.setCount(size);

        return postsResponse;
    }

//    public int getCountPosts(){
//        List<Post> listPost = postRepository.findAllPosts();
//
//        return listPost.size();
//    }

}
