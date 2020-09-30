package main.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SettingsResponse {

    @JsonProperty("MULTIUSER_MODE")
    private boolean multiuseMode;
    @JsonProperty("POST_PREMODERATION")
    private boolean postPremoderation;
    @JsonProperty("STATISTIC_IS_PUBLIC")
    private boolean statisticIsPublic;

    public boolean isMultiuseMode() {
        return multiuseMode;
    }

    public void setMultiuseMode(boolean multiuseMode) {
        this.multiuseMode = multiuseMode;
    }

    public boolean isPostPremoderation() {
        return postPremoderation;
    }

    public void setPostPremoderation(boolean postPremoderation) {
        this.postPremoderation = postPremoderation;
    }

    public boolean isStatisticIsPublic() {
        return statisticIsPublic;
    }

    public void setStatisticIsPublic(boolean statisticIsPublic) {
        this.statisticIsPublic = statisticIsPublic;
    }
}
