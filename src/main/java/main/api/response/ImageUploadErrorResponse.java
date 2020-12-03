package main.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ImageUploadErrorResponse {
//    {
//        "result": false,
//            "errors": {
//        "image": "Размер файла превышает допустимый размер"
//    }
//    }

    @JsonProperty("image")
    private String image;


    public void setImage() {
        this.image = "Размер файла превышает допустимый размер";
    }

}
