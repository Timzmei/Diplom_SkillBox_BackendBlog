package main.service;

import main.api.response.ResultResponse;
import main.model.User;
import main.model.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RestoreService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailSender mailSender;

    public ResultResponse checkEmail(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));

        String codeHash = UUID.randomUUID().toString();

        userRepository.saveCode(email, codeHash);
        String message = String.format(
                "Hello, %s! \n" +
                        "Reset your password. Please, visit next link: http://localhost:8080/login/change-password/%s",
                user.getName(),
                codeHash
        );


        mailSender.mailSender(email, "ActivationCode", message);

        ResultResponse resultResponse = new ResultResponse(true);

        return resultResponse;
    }
}
