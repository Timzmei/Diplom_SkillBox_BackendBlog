package main.api.request;

import lombok.Data;
import main.model.repo.CaptchaRepository;
import main.model.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Data
public class PasswordRequest {

    private final String code;
    private final String password;
    private final String captcha;
    private final String captcha_secret;


}
