package main.service;

import main.api.response.*;
import main.model.*;
import main.model.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private UserRepository userRepository;

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

        Page<Post> postsPage;

        switch (mode) {
            case "popular":
                pageable = PageRequest.of(offset, limit);
                postsPage = postRepository.findAllPostsByCommentsDesc(pageable);
                break;
            case "best":
                pageable = PageRequest.of(offset, limit);
                postsPage = postRepository.findAllPostsByVotesDesc(pageable);
                break;
            case "early":
                postsPage = postRepository.findAllPostsByTime(pageable);
                break;
            default:
                postsPage = postRepository.findAllPostsByTimeDesc(pageable);
                break;
        }

        return createPostsResponse(postsPage, postRepository.findAllPosts().size());
    }

    public PostsResponse getPostsSearch(int offset, int limit, String query) {
        Pageable pageable;
        pageable = PageRequest.of(offset, limit);
        Page<Post> pageOfTags = postRepository.findAllPostsBySearch(query, pageable);

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

    public PostResponse getPostById(int id, Principal principal) {

        List<PostComments> commentsList = commentRepository.findComments(id);
        List<String> tagList = tagRepository.getTagsByPost(id);

//        System.out.println(tagList);

        List<CommentResponse> commentResponseList = new ArrayList<>();
        for (PostComments c : commentsList) {
            commentResponseList.add(new CommentResponse(c));
        }

        Post post;

        if (!(principal == null)) {

            post = postRepository.findPostById(id);

            if (post == null){
                return null;
            }

            User user = userRepository.findByEmail(principal.getName())
                    .orElseThrow(() -> new UsernameNotFoundException("user not found"));

            if (!(post.getUser().getId() == user.getId()) || user.getIsModerator() == 0) {
                postRepository.updateViewPost(post.getId(), post.getViewCount() + 1);
            }
        }
        else {
            post = postRepository.findPostAcceptedById(id);

            if (post == null){
                return null;
            }

            postRepository.updateViewPost(post.getId(), post.getViewCount() + 1);
        }
//        post = postRepository.findPostById(id);

        return new PostResponse(commentResponseList, post, tagList);
    }


    private PostsResponse createPostsResponse(Page<Post> pageOfTags, int size){

        List<PostResponseForList> postResponseList = new ArrayList<>();
        for (Post p : pageOfTags) {
//            System.out.println(p.getComments());
//            System.out.println(p.getLike());
//            System.out.println(p.getTags());

            postResponseList.add(new PostResponseForList(p));
        }
        PostsResponse postsResponse = new PostsResponse();
        postsResponse.setPosts(postResponseList);
        postsResponse.setCount(size);

        return postsResponse;
    }

    public PostsResponse getPostsModeration(int offset, int limit, String status, Principal principal) {
        Pageable pageable;
        pageable = PageRequest.of(offset, limit);

        Page<Post> pageModerate;

        if (status.equals("new")){
            pageModerate = postRepository.findPostsModeration(status, pageable);

        }
        else {

            User user = userRepository.findByEmail(principal.getName())
                    .orElseThrow(() -> new UsernameNotFoundException("user not found"));

            pageModerate = postRepository.findPostsMyModerate(status, user.getId(), pageable);

        }

        return createPostsResponse(pageModerate, (int)pageModerate.getTotalElements());
    }

    public PostsResponse getPostsMy(int offset, int limit, String status, Principal principal) {
        Pageable pageable;
        pageable = PageRequest.of(offset, limit);


        switch (status) {
            case "inactive": {

                Page<Post> pageMy = postRepository.findPostsMyInactive(pageable, principal.getName());
                return createPostsResponse(pageMy, (int) pageMy.getTotalElements());

            }
            case "pending": {
                Page<Post> pageMy = postRepository.findPostsMyIsactive("NEW", principal.getName(), pageable);
                return createPostsResponse(pageMy, (int) pageMy.getTotalElements());

            }
            case "declined": {
                Page<Post> pageMy = postRepository.findPostsMyIsactive("DECLINED", principal.getName(), pageable);
                return createPostsResponse(pageMy, (int) pageMy.getTotalElements());

            }
            case "published": {
                Page<Post> pageMy = postRepository.findPostsMyIsactive("ACCEPTED", principal.getName(), pageable);
                return createPostsResponse(pageMy, (int) pageMy.getTotalElements());

            }
        }

        return null;
    }

    @Transactional
    public PostApiPostResponse postPosts(long timestamp, byte active, String title, String text, List<String> tags, Principal principal) {

        Date datePost = setDatePost(timestamp);

        PostApiPostResponse postApiPostResponse;

        if (title.length() < 3 || text.length() < 50){
            ErrorsResponse errorsResponse = new ErrorsResponse("Заголовок не установлен", "Текст публикации слишком короткий");

            postApiPostResponse = new PostApiPostResponse(false, errorsResponse);
        }

        else {
            User user = userRepository.findByEmail(principal.getName())
                    .orElseThrow(() -> new UsernameNotFoundException("user not found"));
//            postRepository.insertPost(datePost, active, title, text, user.getId());

            Post post = new Post();
            post.setTime(datePost);
            post.setIsActive(active);
            post.setTitle(title);
            post.setText(text);
            post.setUser(user);

            post = postRepository.save(post);

            for (String t: tags
            ) {
//                tagRepository.insertTag(t);
//                tag2PostRepository.insertTag2Post(id, tagRepository.getByName(t));
                Tag tag = new Tag();
                tag.setName(t);
                Tags2Post tags2Post = new Tags2Post();
                tags2Post.setPostId(post);
                tags2Post.setTagId(tagRepository.save(tag));
                tag2PostRepository.save(tags2Post);

            }

            postApiPostResponse = new PostApiPostResponse(true);

        }

        return postApiPostResponse;
    }

    public PostApiPostResponse putPosts(long timestamp, byte active, String title, String text, List<String> tags, int id) {

        Date datePost = setDatePost(timestamp);

        PostApiPostResponse postApiPostResponse;

        if (title.length() < 3 || text.length() < 50){
            ErrorsResponse errorsResponse = new ErrorsResponse("Заголовок не установлен", "Текст публикации слишком короткий");
            postApiPostResponse = new PostApiPostResponse(false, errorsResponse);
        }

        else {
            postRepository.updatePost(datePost, active, title, text, id);

            List<String> oldListTags = tagRepository.getTagsByPost(id);

            Post post = postRepository.findPostById(id);

            for (String t: tags) {

                if (!oldListTags.contains(t)){
                    Tag tag = new Tag();
                    tag.setName(t);
                    Tags2Post tags2Post = new Tags2Post();
                    tags2Post.setPostId(post);
                    tags2Post.setTagId(tagRepository.save(tag));
                    tag2PostRepository.save(tags2Post);
                }
                else {
                    oldListTags.remove(t);
                }
            }

            if (oldListTags.size() > 0) {
                for (String t : oldListTags) {
                    tag2PostRepository.delete(tag2PostRepository.getTags2Post(tagRepository.getByName(t), id));
                }
            }

            postApiPostResponse = new PostApiPostResponse(true);

        }

        return postApiPostResponse;
    }


    private Date setDatePost(long timestamp){

        Date dateNow = new Date();
        Date datePost = new Date(timestamp);

        if (datePost.before(dateNow)){
            datePost = dateNow;
        }
        return datePost;
    }


//    public int getCountPosts(){
//        List<Post> listPost = postRepository.findAllPosts();
//
//        return listPost.size();
//    }

}
