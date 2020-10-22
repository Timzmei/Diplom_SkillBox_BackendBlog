package main.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import main.model.Tag;

import java.util.List;

public class TagsResponse {


    @JsonProperty("tags")
    private List<Tag> tags;

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
