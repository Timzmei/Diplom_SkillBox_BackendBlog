package main.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ImageResponse {

    @JsonProperty("result")
    private Boolean result;
    @JsonProperty("errors")
    private ErrorsResponse errors;

    public ImageResponse() {
        this.result = false;
        this.errors = setErrors();
    }

    public ErrorsResponse setErrors() {

        ErrorsResponse errorsResponse = new ErrorsResponse("Размер файла превышает допустимый размер");
        return this.errors = errorsResponse;
    }
}
