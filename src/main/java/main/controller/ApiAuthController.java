package main.controller;

import main.api.response.AuthChekResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiAuthController {

    private final AuthChekResponse authChekResponse;

    public ApiAuthController(AuthChekResponse authChekResponse) {
        this.authChekResponse = authChekResponse;
    }


    @GetMapping("/api/auth/check")
    private AuthChekResponse authchek(){
        return authChekResponse;
    }


}
