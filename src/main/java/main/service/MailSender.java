package main.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailSender {

    @Autowired
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private final String username;

    public MailSender(JavaMailSender mailSender, String username) {
        this.mailSender = mailSender;
        this.username = username;
    }

    public void mailSender(String mail, String subject, String message){
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(username);
        mailMessage.setTo(mail);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }
}
