package main.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

public class RegisterResponse {

    @JsonProperty("result")
    private Boolean result;
    @JsonProperty("errors")
    private RegisterErrorResponse errors;

    public RegisterResponse(Boolean result) {
        this.result = result;
    }

    public RegisterResponse(Boolean result, RegisterErrorResponse errors) {
        this.result = result;
        this.errors = errors;
    }


}
