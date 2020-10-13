package main.controller;

import main.api.response.PostsResponse;
import main.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/api/post") // все запросы этого контроллера будут начинаться с этого префикс
public class ApiPostController {

    private final PostService postService;


    @Autowired // через конструктор внедряем зависимость сервиса в контроллер
    public ApiPostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/api/post") // конкретный запрос обрабатываем
    public ResponseEntity<PostsResponse> getPosts(
            @RequestParam(required = false, defaultValue = "0") int offset, // первый параметр, он необязателен, и если его нет, то значение будет установлено 0
            @RequestParam(required = false, defaultValue = "10") int limit,
            @RequestParam(required = false, defaultValue = "recent") String mode) {

        System.out.println(12);


        return ResponseEntity.ok(postService.getPosts(offset, limit, mode)); // получаем ответ от сервиса, в который передаем параметры
    }
}