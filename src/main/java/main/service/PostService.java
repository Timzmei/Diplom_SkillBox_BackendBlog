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

import java.security.Principal;
import java.util.*;


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

        if (mode.equals("recent")) {
            postsPage = postRepository.findAllPostsByTimeDesc(pageable);
        } else if (mode.equals("popular")) {
            postsPage = postRepository.findAllPostsByCommentsDesc(pageable);
        } else if (mode.equals("best")) {
            postsPage = postRepository.findAllPostsByVotesDesc(pageable);
        } else if (mode.equals("early")) {
            postsPage = postRepository.findAllPostsByTime(pageable);
        } else {
            postsPage = postRepository.findAllPostsByTimeDesc(pageable);
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

        PostResponse postResponse = new PostResponse(commentResponseList, post, tagList);
        return postResponse;
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

        String email = "\'" + principal.getName() + "\'";


        if (status.equals("inactive")){

//            System.out.println("email - " + email);

            Page<Post> pageMy = postRepository.findPostsMyInactive(pageable, principal.getName());
            return createPostsResponse(pageMy, (int)pageMy.getTotalElements());

        }
        else if (status.equals("pending")){
            Page<Post> pageMy = postRepository.findPostsMyIsactive("NEW", principal.getName(), pageable);
            return createPostsResponse(pageMy, (int)pageMy.getTotalElements());

        }
        else if (status.equals("declined")){
            Page<Post> pageMy = postRepository.findPostsMyIsactive("DECLINED", principal.getName(), pageable);
            return createPostsResponse(pageMy, (int)pageMy.getTotalElements());

        }
        else if (status.equals("published")){
            Page<Post> pageMy = postRepository.findPostsMyIsactive("ACCEPTED", principal.getName(), pageable);
            return createPostsResponse(pageMy, (int)pageMy.getTotalElements());

        }

        return null;
    }

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

    public PostApiPostResponse putPosts(long timestamp, byte active, String title, String text, List<String> tags, int id, Principal principal) {

        Date datePost = setDatePost(timestamp);

        PostApiPostResponse postApiPostResponse;

        if (title.length() < 3 || text.length() < 50){
            ErrorsResponse errorsResponse = new ErrorsResponse("Заголовок не установлен", "Текст публикации слишком короткий");
            postApiPostResponse = new PostApiPostResponse(false, errorsResponse);
        }

        else {
            postRepository.updatePost(datePost, active, title, text, id);
            for (String t: tags
            ) {
                tagRepository.insertTag(t);
                tag2PostRepository.insertTag2Post(id, tagRepository.getByName(t));
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
