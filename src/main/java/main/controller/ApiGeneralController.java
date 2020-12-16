package main.controller;

import main.api.request.CommentRequest;
import main.api.request.ModerateRequest;
import main.api.request.ProfileRequest;
import main.api.response.*;
import main.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

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


    public ApiGeneralController(SettingsService settingsService, InitResponse initResponse, TagService tagService, CalendarService calendarService, StorageService storageService, CommentService commentService, ModerateService moderateService, StatisticService statisticService) {
        this.settingsService = settingsService;
        this.initResponse = initResponse;
        this.tagService = tagService;
        this.calendarService = calendarService;
        this.storageService = storageService;
        this.commentService = commentService;
        this.moderateService = moderateService;
        this.statisticService = statisticService;
    }

    @GetMapping("/api/settings")
    private SettingsResponse settings(){
        return settingsService.getGlobalSettings();
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
    // ДОДЕЛАТЬ - В случае, если публичный показ статистики блога запрещён (см. соответствующий параметр в global_settings) и текущий пользователь не модератор, должна выдаваться ошибка 401
    private ResponseEntity getStatisticAll(
            Principal principal) {
        return statisticService.getAll(principal);

    }

    @PostMapping("/api/profile/my")
    public ResponseEntity postProfileMy(
            @RequestBody ProfileRequest profileRequest,
            Principal principal) {
        return userService.postModerate(profileRequest, principal);
    }

}
