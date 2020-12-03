package main.controller;

import main.api.response.*;
import main.service.CalendarService;
import main.service.SettingsService;
import main.service.StorageService;
import main.service.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.naming.SizeLimitExceededException;
import java.io.IOException;

@RestController
public class ApiGeneralController {

    private final SettingsService settingsService;
    private final InitResponse initResponse;
    private final TagService tagService;
    private final CalendarService calendarService;
    private final StorageService storageService;


    public ApiGeneralController(SettingsService settingsService, InitResponse initResponse, TagService tagService, CalendarService calendarService, StorageService storageService) {
        this.settingsService = settingsService;
        this.initResponse = initResponse;
        this.tagService = tagService;
        this.calendarService = calendarService;
        this.storageService = storageService;
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

    @PostMapping("/api/image") // необходимо дописать ответ в случае ошибки
    private ResponseEntity<Object> fileUpload(@RequestParam("image") MultipartFile file) throws IOException {


        return storageService.store(file);





    }

}
