package main.service;

import main.api.request.RegisterRequest;
import main.api.response.RegisterErrorResponse;
import main.api.response.RegisterResponse;
import main.model.repo.CaptchaRepository;
import main.model.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RegisterService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CaptchaRepository captchaRepository;

    public RegisterResponse checkRegister(RegisterRequest registerRequest){

//        "result": false,
//                "errors": {
//            "email": "Этот e-mail уже зарегистрирован",
//                    "name": "Имя указано неверно",
//                    "password": "Пароль короче 6-ти символов",
//                    "captcha": "Код с картинки введён неверно"

        boolean result = true;
        RegisterErrorResponse registerErrorResponse = new RegisterErrorResponse();

        if (registerRequest.getPassword().length() < 6){
            registerErrorResponse.setPassword();
            result = false;
        }
        if (registerRequest.getE_mail().equals(userRepository.findUserByEmail(registerRequest.getE_mail()))){
            registerErrorResponse.setEmail();
            result = false;
        }
        if (!(registerRequest.getCaptcha().equals(captchaRepository.checkCaptcha(registerRequest.getCaptcha_secret())))){
            registerErrorResponse.setCaptcha();
            result = false;
        }
        if (!registerRequest.getName().matches("[\\w]+")){
            registerErrorResponse.setName();
            result = false;
        }

        if (!result){
            return new RegisterResponse(false, registerErrorResponse);
        }
        else {
            userRepository.insertUser(registerRequest.getE_mail(), registerRequest.getName(), new Date(), BCrypt.hashpw(registerRequest.getPassword(), BCrypt.gensalt(12)));
            return new RegisterResponse(true);
        }

    }
}
