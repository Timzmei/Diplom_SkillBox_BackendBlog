package main.api.response;

public class PostApiPostResponse {

    private boolean result;
    private ErrorsResponse errors;

    public PostApiPostResponse(boolean result) {
        this.result = result;
    }

    public PostApiPostResponse(boolean result, ErrorsResponse errors) {
        this.result = result;
        this.errors = errors;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public ErrorsResponse getErrors() {
        return errors;
    }

    public void setErrors(ErrorsResponse errors) {
        this.errors = errors;
    }
}
