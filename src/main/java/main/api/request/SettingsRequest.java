package main.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SettingsRequest {

    private boolean multiuseMode;
    private boolean postPremoderation;
    private boolean statisticIsPublic;

}
