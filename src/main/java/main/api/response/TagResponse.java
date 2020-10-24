package main.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import main.model.Tag;

public class TagResponse {

    @JsonProperty("name")
    private String name;
    @JsonProperty("weight")
    private double weight;

    public TagResponse(String name, double weight) {
        this.name = name;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public String setName(String name) {
//        this.name = tag.getName();
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public double setWeight(double i) {
//        this.weight = 1 / tag.getId();
        return i;
    }
}
