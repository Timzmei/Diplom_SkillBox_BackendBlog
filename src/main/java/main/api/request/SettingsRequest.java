package main.api.request;

import lombok.Data;

@Data
public class SettingsRequest {

    private boolean multiuseMode;
    private boolean postPremoderation;
    private boolean statisticIsPublic;

}
