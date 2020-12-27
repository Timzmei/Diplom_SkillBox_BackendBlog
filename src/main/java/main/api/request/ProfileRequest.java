package main.api.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Data
public class ProfileRequest {

    private String name;
    private String email;
    private String password;
    private Byte removePhoto;
//    private MultipartFile photo;
}
