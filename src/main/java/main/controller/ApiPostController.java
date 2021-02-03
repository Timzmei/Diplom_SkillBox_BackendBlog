package main.controller;

import main.api.request.LikeRequest;
import main.api.request.PostRequest;
import main.api.response.PostApiPostResponse;
import main.api.response.PostResponse;
import main.api.response.PostsResponse;
import main.service.PostService;
import main.service.PostVotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/post") // все запросы этого контроллера будут начинаться с этого префикс
public class ApiPostController {

    private final PostService postService;

    private final PostVotesService postVotesService;


    @Autowired // через конструктор внедряем зависимость сервиса в контроллер
    public ApiPostController(PostService postService, PostVotesService postVotesService) {
        this.postService = postService;
        this.postVotesService = postVotesService;
    }

    @GetMapping("") // конкретный запрос обрабатываем
    public ResponseEntity<PostsResponse> getPosts(
            @RequestParam(required = false, defaultValue = "0") int offset, // первый параметр, он необязателен, и если его нет, то значение будет установлено 0
            @RequestParam(required = false, defaultValue = "10") int limit,
            @RequestParam(required = false, defaultValue = "recent") String mode) {

        return ResponseEntity.ok(postService.getPosts(offset, limit, mode)); // получаем ответ от сервиса, в который передаем параметры
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<PostApiPostResponse> postPost(
            @RequestBody PostRequest postRequest,
            Principal principal) {


        return ResponseEntity.ok(postService.postPosts(postRequest.getTimestamp(), postRequest.getActive(), postRequest.getTitle(), postRequest.getText(), postRequest.getTags(), principal));
    }

    @GetMapping("/search")
    public ResponseEntity<PostsResponse> getPostsSearch(
            @RequestParam(required = false, defaultValue = "0") int offset,
            @RequestParam(required = false, defaultValue = "10") int limit,
            @RequestParam(required = false, defaultValue = " ") String query) {

        return ResponseEntity.ok(postService.getPostsSearch(offset, limit, query));
    }

    @GetMapping("/byDate")
    public ResponseEntity<PostsResponse> getPostsByDate(
            @RequestParam(required = false, defaultValue = "0") int offset,
            @RequestParam(required = false, defaultValue = "10") int limit,
            @RequestParam(required = false, defaultValue = "") String date) {

        return ResponseEntity.ok(postService.getPostsByDate(offset, limit, date));
    }

    @GetMapping("/byTag")
    public ResponseEntity<PostsResponse> getPostsByTag(
            @RequestParam(required = false, defaultValue = "0") int offset,
            @RequestParam(required = false, defaultValue = "10") int limit,
            @RequestParam(required = false, defaultValue = "") String tag) {

        return ResponseEntity.ok(postService.getPostsByTag(offset, limit, tag));
    }

    @GetMapping("/moderation")
    @PreAuthorize("hasAuthority('user:moderate')")
    public ResponseEntity<PostsResponse> getPostsModeration(
            @RequestParam(required = false, defaultValue = "0") int offset,
            @RequestParam(required = false, defaultValue = "10") int limit,
            @RequestParam(required = false, defaultValue = "") String status,
            Principal principal) {

        return ResponseEntity.ok(postService.getPostsModeration(offset, limit, status, principal));
    }

    @GetMapping("/my")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<PostsResponse> getPostsMy(
            @RequestParam(required = false, defaultValue = "0") int offset,
            @RequestParam(required = false, defaultValue = "10") int limit,
            @RequestParam(required = false, defaultValue = "") String status,
            Principal principal) {

        return ResponseEntity.ok(postService.getPostsMy(offset, limit, status, principal));
    }

    @GetMapping("/{id}")
    public Object getPostsById(
            @PathVariable int id,
            Principal principal) {
        PostResponse postResponse = postService.getPostById(id, principal);

        if (postResponse == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return postResponse;
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<PostApiPostResponse> putPost(
            @PathVariable int id,
            @RequestBody PostRequest postRequest) {


        return ResponseEntity.ok(postService.putPosts(
                postRequest.getTimestamp(),
                postRequest.getActive(),
                postRequest.getTitle(),
                postRequest.getText(),
                postRequest.getTags(),
                id
        ));
    }

    @PostMapping("/like")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity postLike(
            @RequestBody LikeRequest likeRequest,
            Principal principal
    ){
       return postVotesService.addLike(likeRequest, principal);
    }

    @PostMapping("/dislike")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity postDislike(
            @RequestBody LikeRequest likeRequest,
            Principal principal
    ){
        return postVotesService.addDislike(likeRequest, principal);
    }



}