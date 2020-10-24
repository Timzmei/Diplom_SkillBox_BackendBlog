package main.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import main.model.Tag;

public class TagResponse {

    @JsonProperty("name")
    private String name;
    @JsonProperty("weight")
    private double weight;

    public TagResponse(Tag tag) {
        this.name = setName(tag);
        this.weight = setWeight(tag);
    }

    public String getName() {
        return name;
    }

    public String setName(Tag tag) {
//        this.name = tag.getName();
        return tag.getName();
    }

    public double getWeight() {
        return weight;
    }

    public double setWeight(Tag tag) {
//        this.weight = 1 / tag.getId();
        return 1 / tag.getId();
    }
}
