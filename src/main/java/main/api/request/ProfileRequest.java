package main.api.request;

import lombok.Data;

import java.io.File;

@Data
public class ProfileRequest {
//    {
//        "photo": <binary_file>,
//            "name":"Sendel",
//            "email":"sndl@mail.ru",
//            "password":"123456",
//            "removePhoto":0
//    }


    private String name;
    private String email;
    private String password;
    private byte removePhoto;
    private File photo;
}
