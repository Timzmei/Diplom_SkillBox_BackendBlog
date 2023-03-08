package main.api.request;

import lombok.Data;

@Data
public class RegisterRequest {
    private String e_mail;
    private String password;
    private String name;
    private String captcha;
    private String captcha_secret;
}
