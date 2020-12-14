package main.api.response;


public class ApiCommentResponse {


    private boolean result;
    private ErrorsResponse errors;
    private String text;
    private int id;

    public ApiCommentResponse(int id) {
        this.id = id;
    }

    public ApiCommentResponse() {
        this.result = setResult();
        this.errors = errors;
    }

    public boolean setResult() {
        return false;
    }

    public ErrorsResponse setErrors() {
        return new ErrorsResponse("", "Текст комментария не задан или слишком короткий");
    }

}
