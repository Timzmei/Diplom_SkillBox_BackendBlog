package main.api.response;

import lombok.Data;

public class PostApiPostResponse {

    private boolean result;
    private PostApiPostErrors errors;

    public PostApiPostResponse(boolean result) {
        this.result = result;
    }

    public PostApiPostResponse(boolean result, PostApiPostErrors errors) {
        this.result = result;
        this.errors = errors;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public PostApiPostErrors getErrors() {
        return errors;
    }

    public void setErrors(PostApiPostErrors errors) {
        this.errors = errors;
    }
}
