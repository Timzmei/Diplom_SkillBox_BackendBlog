package main.api.request;

import lombok.Data;

@Data
public class PasswordRequest {

    private final String code;
    private final String password;
    private final String captcha;
    private final String captcha_secret;


}
