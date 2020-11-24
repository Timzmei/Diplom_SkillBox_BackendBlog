package main.controller;

import main.api.response.PostResponse;
import main.api.response.PostsResponse;
import main.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post") // все запросы этого контроллера будут начинаться с этого префикс
public class ApiPostController {

    private final PostService postService;


    @Autowired // через конструктор внедряем зависимость сервиса в контроллер
    public ApiPostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("") // конкретный запрос обрабатываем
    public ResponseEntity<PostsResponse> getPosts(
            @RequestParam(required = false, defaultValue = "0") int offset, // первый параметр, он необязателен, и если его нет, то значение будет установлено 0
            @RequestParam(required = false, defaultValue = "10") int limit,
            @RequestParam(required = false, defaultValue = "recent") String mode) {

        return ResponseEntity.ok(postService.getPosts(offset, limit, mode)); // получаем ответ от сервиса, в который передаем параметры
    }

    @PostMapping("")
    public ResponseEntity<PostsResponse> postPost(
            @RequestParam(required = false, defaultValue = "0") int offset, // первый параметр, он необязателен, и если его нет, то значение будет установлено 0
            @RequestParam(required = false, defaultValue = "10") int limit,
            @RequestParam(required = false, defaultValue = "recent") String mode) {

        return ResponseEntity.ok(postService.getPosts(offset, limit, mode)); // получаем ответ от сервиса, в который передаем параметры
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
            @RequestParam(required = false, defaultValue = "") String status) {

        return ResponseEntity.ok(postService.getPostsModeration(offset, limit, status));
    }

    @GetMapping("/my")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<PostsResponse> getPostsMy(
            @RequestParam(required = false, defaultValue = "0") int offset,
            @RequestParam(required = false, defaultValue = "10") int limit,
            @RequestParam(required = false, defaultValue = "") String status) {

        return ResponseEntity.ok(postService.getPostsMy(offset, limit, status));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostsById(
            @PathVariable int id) {

        return ResponseEntity.ok(postService.getPostById(id));
    }



}