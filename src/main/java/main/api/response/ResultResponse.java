package main.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


public class ResultResponse {

    @JsonProperty("result")
    private Boolean result;

    public ResultResponse(Boolean result) {
        this.result = setResult(result);
    }

    public Boolean setResult(Boolean result) {
        return result;
    }
}
