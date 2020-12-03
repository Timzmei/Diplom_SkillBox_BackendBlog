package main.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RestoreResponse {

    @JsonProperty("result")
    private Boolean result;
}
