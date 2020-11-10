package main.controller;

import main.api.response.AuthCaptchaResponse;
import main.api.response.AuthChekResponse;
import main.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/auth") // все запросы этого контроллера будут начинаться с этого префикс
public class ApiAuthController {

    private final AuthChekResponse authChekResponse;
    private final CaptchaService captchaService;

    @Autowired
    public ApiAuthController(AuthChekResponse authChekResponse, CaptchaService captchaService) {
        this.authChekResponse = authChekResponse;
        this.captchaService = captchaService;
    }


    @GetMapping("/check")
    private AuthChekResponse authchek(){
        return authChekResponse;
    }

    @GetMapping("/captcha")
    public AuthCaptchaResponse getCaptcha() throws IOException {

        return captchaService.getCaptcha();
    }


}
