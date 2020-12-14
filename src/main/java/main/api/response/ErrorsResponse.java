package main.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

public class ErrorsResponse {

    @JsonProperty("title")
    private String title;

    @JsonProperty("text")
    private String text;

    @JsonProperty("image")
    private String image;


    public ErrorsResponse(String title, String text) {
        this.title = setTitle(title);
        this.text = setText(text);
    }

    public ErrorsResponse(String image) {
        this.image = setImage(image);
    }



    public String setTitle(String title) {
        return title;
    }



    public String setText(String text) {
        return text;
    }

    public String setImage(String image) {
        return image;
    }
}
