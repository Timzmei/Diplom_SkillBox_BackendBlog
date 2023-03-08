package main.api.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ApiCommentResponse {

    @JsonProperty("id")
    private int id;

    public ApiCommentResponse(int id) {
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

