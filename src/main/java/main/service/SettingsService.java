package main.service;

import main.api.request.SettingsRequest;
import main.api.response.SettingsResponse;
import main.model.repo.GlobalSettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class SettingsService {

    @Autowired
    private final GlobalSettingsRepository globalSettingsRepository;

    public SettingsService(GlobalSettingsRepository globalSettingsRepository) {
        this.globalSettingsRepository = globalSettingsRepository;
    }

    public SettingsResponse getGlobalSettings(){
//        System.out.println(globalSettingsRepository.findAllGlobalSettings("MULTIUSER_MODE").getValue());

        SettingsResponse settingsResponse = new SettingsResponse();
        settingsResponse.setMultiuseMode(globalSettingsRepository.findAllGlobalSettings("MULTIUSER_MODE").getValue().equals("YES"));

        settingsResponse.setPostPremoderation(globalSettingsRepository.findAllGlobalSettings("POST_PREMODERATION").getValue().equals("YES"));
        settingsResponse.setStatisticIsPublic(globalSettingsRepository.findAllGlobalSettings("STATISTICS_IS_PUBLIC").getValue().equals("YES"));
        return settingsResponse;
    }

    @PreAuthorize("hasAuthority('user:moderate')")
    public ResponseEntity<Boolean> putGlobalSettings(SettingsRequest settingsRequest) {
        String MULTIUSER_MODE = settingsRequest.isMultiuseMode() ? "YES" : "NO";
        String POST_PREMODERATION = settingsRequest.isPostPremoderation() ? "YES" : "NO";
        String STATISTICS_IS_PUBLIC = settingsRequest.isStatisticIsPublic() ? "YES" : "NO";

        globalSettingsRepository.insertSettings("MULTIUSER_MODE", MULTIUSER_MODE);
        globalSettingsRepository.insertSettings("POST_PREMODERATION", POST_PREMODERATION);
        globalSettingsRepository.insertSettings("STATISTICS_IS_PUBLIC", STATISTICS_IS_PUBLIC);

    return new ResponseEntity<>(true, HttpStatus.OK);

    }
}
