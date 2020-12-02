package main.api.request;

import lombok.Data;

import java.util.List;

@Data
public class PostRequest {

//    {
//        "timestamp":1592338706,
//            "active":1,
//            "title":"заголовок",
//            "tags":["java","spring"],
//        "text":"Текст поста включащий <b>тэги форматирования</b>"
//    }

    private long timestamp;
    private byte active;
    private String title;
    private String text;
    private List<String> tags;



}
