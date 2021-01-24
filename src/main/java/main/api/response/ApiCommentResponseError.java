package main.api.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ApiCommentResponseError {

    @JsonProperty("result")
    private boolean result;

    @JsonProperty("errors")
    private ErrorCommentResponse errors;


    public ApiCommentResponseError(boolean result) {
        this.result = setResult(result);
        this.errors = setErrors();
    }

    public boolean setResult(boolean result) {
        return result;
    }

    public ErrorCommentResponse setErrors() {
        return new ErrorCommentResponse("Текст комментария не задан или слишком короткий");
    }

}

class ErrorCommentResponse{
    @JsonProperty("text")
    private final String text;

    public ErrorCommentResponse(String text) {
        this.text = setText(text);
    }

    public String setText(String text) {
        return text;
    }
}
