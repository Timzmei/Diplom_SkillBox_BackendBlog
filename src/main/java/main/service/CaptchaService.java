package main.service;

import com.github.cage.Cage;
import com.github.cage.GCage;
import main.api.response.AuthCaptchaResponse;
import main.model.repo.CaptchaRepository;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;
import java.util.Date;
import java.util.Random;

@Service
@Transactional
public class CaptchaService {

    @Autowired
    private CaptchaRepository captchaRepository;

    public AuthCaptchaResponse getCaptcha() throws IOException {

        final Cage cage = new GCage();

        String token = cage.getTokenGenerator().next();
        String secretKey = generateSecretKey();
        String base64String = ConvertImageToBase64(token, cage);

//        AuthCaptchaResponse authCaptchaResponse = new AuthCaptchaResponse(secretKey, base64String);

        AuthCaptchaResponse authCaptchaResponse = new AuthCaptchaResponse();

        authCaptchaResponse.setSecret(secretKey);
        authCaptchaResponse.setImage(base64String);


        captchaRepository.insertCaptcha(token, secretKey, new Date());



        return authCaptchaResponse;
    }



    private String ConvertImageToBase64(String token, Cage cage) throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write( ImResizer(cage.drawImage(token)), "jpg", baos );
        baos.flush();
        byte[] imageInByte = baos.toByteArray();
        baos.close();

        String encodedString = "data:image/png;base64," + Base64.getEncoder().encodeToString(imageInByte);

        return encodedString;
    }

    private BufferedImage ImResizer (BufferedImage image){

        int newWidth = 100;
        int newHeight = 35;

        BufferedImage newImage = new BufferedImage(
                newWidth, newHeight, BufferedImage.TYPE_INT_RGB
        );

        Graphics2D g = newImage.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        g.drawImage(image, 0, 0, newWidth, newHeight, null);

        g.dispose();

        return newImage;
    }
    private String generateSecretKey()
    {
        String generatedString = RandomStringUtils.randomAlphanumeric(10);

        return generatedString;
    }
}
