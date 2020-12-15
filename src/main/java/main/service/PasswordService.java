package main.service;

import main.api.request.PasswordRequest;
import main.api.response.PasswordResponse;
import main.api.response.ResultResponse;
import main.model.User;
import main.model.repo.CaptchaRepository;
import main.model.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {

    @Autowired
    private CaptchaRepository captchaRepository;

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity checkCode(PasswordRequest passwordRequest) {


        String codeCaptcha = captchaRepository.checkCaptcha(passwordRequest.getCaptcha_secret());

        if(!codeCaptcha.equals(passwordRequest.getCaptcha()) || passwordRequest.getPassword().length() < 6 || !(userRepository.findUserByCode(passwordRequest.getCode()) == null)) {

            return new ResponseEntity(new PasswordResponse(), HttpStatus.OK);
        }
        else{
            userRepository.changePassword(BCrypt.hashpw(passwordRequest.getPassword(), BCrypt.gensalt(12)), passwordRequest.getCode());
        }


        return new ResponseEntity(new ResultResponse(true), HttpStatus.OK);
    }
}
