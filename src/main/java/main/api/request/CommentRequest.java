package main.api.request;

import lombok.Data;

@Data
public class CommentRequest {


    private Integer parent_id;
    private int post_id;
    private String text;


}
