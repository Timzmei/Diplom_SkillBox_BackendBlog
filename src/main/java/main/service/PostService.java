package main.service;

import main.api.response.CommentResponse;
import main.api.response.PostResponse;
import main.api.response.PostsResponse;
import main.model.Post;
import main.model.PostComments;
import main.model.Tag;
import main.model.repo.CommentRepository;
import main.model.repo.PostRepository;
import main.model.repo.Tag2PostRepository;
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

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private Tag2PostRepository tag2PostRepository;

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

        if (mode.equals("recent")) {
            pageOfTags = postRepository.findAllOrderByTimeDesc(pageable);
        } else if (mode.equals("popular")) {
            pageOfTags = postRepository.findAllOrderByCommentsDesc(pageable);
        } else if (mode.equals("best")) {
            pageOfTags = postRepository.findAllOrderByVotesDesc(pageable);
        } else if (mode.equals("early")) {
            pageOfTags = postRepository.findAllOrderByTime(pageable);
        }

        return createPostsResponse(pageOfTags, postRepository.findAllPosts().size());
    }

    public PostsResponse getPostsSearch(int offset, int limit, String query) {
        Pageable pageable;
        pageable = PageRequest.of(offset, limit);
        Page<Post> pageOfTags = postRepository.findAllOrderBySearch(query, pageable);

        return createPostsResponse(pageOfTags, (int)pageOfTags.getTotalElements());
    }

    public PostsResponse getPostsByDate(int offset, int limit, String date) {
        Pageable pageable;
        pageable = PageRequest.of(offset, limit);
        Page<Post> pageByDate = postRepository.findAllPostsByDate(date, pageable);

        return createPostsResponse(pageByDate, (int)pageByDate.getTotalElements());
    }

    public PostsResponse getPostsByTag(int offset, int limit, String tag) {
        Pageable pageable;
        pageable = PageRequest.of(offset, limit);
        Page<Post> pageByTag = postRepository.findAllPostsByTag(tag, pageable);

        return createPostsResponse(pageByTag, (int)pageByTag.getTotalElements());
    }

    public PostResponse getPostById(int id) {

        List<PostComments> commentsList = commentRepository.findComments(id);
        List<String> tagList = tag2PostRepository.getTagsByPost(id);

        List<CommentResponse> commentResponseList = new ArrayList<>();
        for (PostComments c : commentsList) {
            commentResponseList.add(new CommentResponse(c));
        }




        return new PostResponse(commentResponseList, postRepository.findPostById(id), tagList);
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

    public PostsResponse getPostsModeration(int offset, int limit, String status) {
        Pageable pageable;
        pageable = PageRequest.of(offset, limit);
        Page<Post> pageModerate = postRepository.findPostsModeration(status, pageable);

        return createPostsResponse(pageModerate, (int)pageModerate.getTotalElements());
    }

    public PostsResponse getPostsMy(int offset, int limit, String status) {
        Pageable pageable;
        pageable = PageRequest.of(offset, limit);

        if (status.equals("inactive")){
            Page<Post> pageMy = postRepository.findPostsMyInactive(pageable);
            return createPostsResponse(pageMy, (int)pageMy.getTotalElements());

        }
        else if (status.equals("pending")){
            Page<Post> pageMy = postRepository.findPostsMyIsactive("NEW", pageable);
            return createPostsResponse(pageMy, (int)pageMy.getTotalElements());

        }
        else if (status.equals("declined")){
            Page<Post> pageMy = postRepository.findPostsMyIsactive("DECLINED", pageable);
            return createPostsResponse(pageMy, (int)pageMy.getTotalElements());

        }
        else if (status.equals("published")){
            Page<Post> pageMy = postRepository.findPostsMyIsactive("ACCEPTED", pageable);
            return createPostsResponse(pageMy, (int)pageMy.getTotalElements());

        }

        return null;
    }


//    public int getCountPosts(){
//        List<Post> listPost = postRepository.findAllPosts();
//
//        return listPost.size();
//    }

}
