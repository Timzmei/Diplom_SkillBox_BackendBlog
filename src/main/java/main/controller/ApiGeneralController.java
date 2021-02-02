package main.controller;

import main.api.request.CommentRequest;
import main.api.request.ModerateRequest;
import main.api.request.ProfileRequest;
import main.api.request.SettingsRequest;
import main.api.response.*;
import main.service.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.security.Principal;

@SuppressWarnings("rawtypes")
@RestController
public class ApiGeneralController {

    private final SettingsService settingsService;
    private final InitResponse initResponse;
    private final TagService tagService;
    private final CalendarService calendarService;
    private final StorageService storageService;
    private final CommentService commentService;
    private final ModerateService moderateService;
    private final StatisticService statisticService;
    private final UserService userService;


    public ApiGeneralController(SettingsService settingsService, InitResponse initResponse, TagService tagService, CalendarService calendarService, StorageService storageService, CommentService commentService, ModerateService moderateService, StatisticService statisticService, UserService userService) {
        this.settingsService = settingsService;
        this.initResponse = initResponse;
        this.tagService = tagService;
        this.calendarService = calendarService;
        this.storageService = storageService;
        this.commentService = commentService;
        this.moderateService = moderateService;
        this.statisticService = statisticService;
        this.userService = userService;
    }

    @GetMapping("/api/settings")
    private SettingsResponse settings(){
        return settingsService.getGlobalSettings();
    }

    @PutMapping("/api/settings")
    private ResponseEntity putSettings(
            @RequestBody (required = false) SettingsRequest settingsRequest,
            Principal principal
    ){
        return settingsService.putGlobalSettings(settingsRequest);
    }

    @GetMapping("/api/init")
    private InitResponse init(){

        return initResponse;
    }

    @GetMapping("/api/tag")
    private TagsResponse getTags(
            @RequestParam(required = false, defaultValue = "") String query) {
        return tagService.getTags(query);

    }

    @GetMapping("/api/calendar")
    private CalendarResponse getCalendar(
            @RequestParam(required = false, defaultValue = "none") String year) {
        return calendarService.getCalendar(year);

    }

    @PostMapping("/api/image")
    @PreAuthorize("hasAuthority('user:write')")
    private ResponseEntity<Object> fileUpload(@RequestParam("image") MultipartFile file) throws IOException {
        return storageService.store(file);

    }

    @PostMapping("/api/comment")
//    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity postComment(
            @RequestBody CommentRequest commentRequest,
            Principal principal) {
        return commentService.postComment(commentRequest, principal);
    }

    @PostMapping("/api/moderation")
    public ResponseEntity postModeration(
            @RequestBody ModerateRequest moderateRequest,
            Principal principal) {
        return moderateService.postModerate(moderateRequest, principal);
    }

    @GetMapping("/api/statistics/my")
    private StatisticResponse getStatisticMy(
            Principal principal) {
        return statisticService.getMy(principal);

    }

    @GetMapping("/api/statistics/all")
    private ResponseEntity getStatisticAll(
            Principal principal) {
        return statisticService.getAll(principal);

    }

    @PostMapping(value = "/api/profile/my", consumes = {"multipart/form-data"})
    public ResponseEntity postProfileMy(
            @RequestPart(value = "photo", required=false) MultipartFile file,
            @RequestPart(value = "name", required=false) String name,
            @RequestPart(value = "email", required=false) String email,
            @RequestPart(value = "password", required=false)String password,
            @RequestPart(value = "removePhoto", required=false) String removePhoto,
            Principal principal) throws Exception {
        return userService.editProfile(file, name, email, password, removePhoto, principal);
    }

    @PostMapping(value = "/api/profile/my", consumes = {"application/json"})
    public ResponseEntity postProfileMyJSON(
            @RequestBody (required = false) ProfileRequest profileRequest,
            Principal principal) {

//        System.out.println("profileRequest: " + profileRequest);
        return userService.editProfile1(profileRequest, principal);

    }



}
