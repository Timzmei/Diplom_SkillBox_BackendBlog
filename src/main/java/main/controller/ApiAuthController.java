package main.controller;

import main.api.request.LoginRequest;
import main.api.request.PasswordRequest;
import main.api.request.RegisterRequest;
import main.api.request.RestoreRequest;
import main.api.response.*;
import main.model.repo.PostRepository;
import main.model.repo.UserRepository;
import main.service.CaptchaService;
import main.service.PasswordService;
import main.service.RegisterService;
import main.service.RestoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/api/auth") // все запросы этого контроллера будут начинаться с этого префикс
public class ApiAuthController {

    private final AuthChekResponse authChekResponse;
    private final CaptchaService captchaService;
    private final RegisterService registerService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final RestoreService restoreService;
    private final PasswordService passwordService;

    @Autowired
    public ApiAuthController(AuthChekResponse authChekResponse, CaptchaService captchaService, RegisterService registerService, AuthenticationManager authenticationManager, UserRepository userRepository, PostRepository postRepository, RestoreService restoreService, PasswordService passwordService) {
        this.authChekResponse = authChekResponse;
        this.captchaService = captchaService;
        this.registerService = registerService;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.restoreService = restoreService;
        this.passwordService = passwordService;
    }




    @GetMapping("/captcha")
    public AuthCaptchaResponse getCaptcha() throws IOException {

        return captchaService.getCaptcha();
    }

    @PostMapping("/register")
    public RegisterResponse checkRegister(
            @RequestBody RegisterRequest registerRequest) {


        return registerService.checkRegister(registerRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) throws AuthenticationException {
        Authentication auth = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(auth);
        User user = (User) auth.getPrincipal();

        return ResponseEntity.ok(getLoginResponse(user.getUsername()));
    }

    @GetMapping("/check")
    public ResponseEntity<LoginResponse> check(Principal principal){
        if(principal == null) {
            return ResponseEntity.ok(new LoginResponse());
        }

        return ResponseEntity.ok(getLoginResponse(principal.getName()));
    }

    @PostMapping("/restore")
    public ResponseEntity<ResultResponse> postRestore(
            @RequestBody RestoreRequest restoreRequest) {

        return ResponseEntity.ok(restoreService.checkEmail(restoreRequest.getEmail()));
    }

    @PostMapping("/password")
    public ResponseEntity postPassword(
            @RequestBody PasswordRequest passwordRequest) {

        return passwordService.checkCode(passwordRequest);
    }


    private LoginResponse getLoginResponse(String email){
        main.model.User currentUser =
                userRepository.findByEmail(email)
                        .orElseThrow(() -> new UsernameNotFoundException(email));
        UserLoginResponse userResponse = new UserLoginResponse();
        userResponse.setEmail(currentUser.getEmail());
        userResponse.setName(currentUser.getName());
        userResponse.setPhoto(currentUser.getPhoto());
        userResponse.setModeration(currentUser.getIsModerator() == 1);
        userResponse.setId(currentUser.getId());
        userResponse.setSettings(currentUser.getIsModerator() == 1);
        userResponse.setModerationCount(email, postRepository);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setResult(true);
        loginResponse.setUserLoginResponse(userResponse);
        return loginResponse;
    }





}
