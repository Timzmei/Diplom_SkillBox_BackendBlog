package main.controller;

import main.api.response.ApiPostResponse;
import main.api.response.PostResponse;
import main.model.repo.PostsRepo;
import main.service.PostService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiPostController {


    private final PostService postService;


    public ApiPostController(PostService postService) {
        this.postService = postService;

    }

    @GetMapping("/api/post")
    private ApiPostResponse apiPostResponse(
            @RequestParam int offset,
            @RequestParam int limit,
            @RequestParam String mode,
            Model model,
            @PageableDefault(sort = {"time"}, direction = Sort.Direction.DESC) Pageable pageable)
    {


        return postService.findPaginated(offset, limit);
    }



}
