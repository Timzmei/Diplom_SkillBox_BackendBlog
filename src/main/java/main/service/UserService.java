package main.service;

import main.api.request.ProfileRequest;
import main.api.response.MyProfileResponse;
import main.api.response.ResultResponse;
import main.model.User;
import main.model.repo.UserRepository;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String uploadPhoto(MultipartFile newPhoto, Principal principal) throws Exception {
        String folder = "profile_photo";

        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));

        String file = folder + "/" + user.getId();
        String resultPath = "src/main/resources/static/" + folder + "/" + user.getId();
//        String resultPath2 = "target/classes/static/" + folder + "/" + user.getId();

        Path uploadDir = Paths.get(resultPath);
        if (!Files.exists(uploadDir)){
            Files.createDirectories(uploadDir);
        }
        Path filePath = uploadDir.resolve(newPhoto.getOriginalFilename());
        File resizeFile = new File(String.valueOf(filePath));
        simpleResizeImage(newPhoto, resizeFile);


        return "/" + file + "/" + newPhoto.getOriginalFilename();
    }

    public void simpleResizeImage(MultipartFile newPhoto, File resizeFile) throws Exception {
        Thumbnails.of(newPhoto.getInputStream()).crop(Positions.CENTER_LEFT).size(36,36).keepAspectRatio(true).toFile(resizeFile);
    }

    public ResponseEntity editProfile1(ProfileRequest profileRequest, Principal principal) {

        String email = principal.getName();
        String name = profileRequest.getName();
        String newEmail = profileRequest.getEmail();
        String password = profileRequest.getPassword();
        Byte removePhoto = profileRequest.getRemovePhoto();

//        System.out.println("profileRequest: " + profileRequest);
//        System.out.println("Имя: " + name);
//        System.out.println("почта: " + newEmail);
//        System.out.println("пароль: " + password);

        MyProfileResponse myProfileResponse = new MyProfileResponse();
        myProfileResponse.setResult(true);


        if (!checkMyProfile(null, name, newEmail, password, principal, myProfileResponse)){
            return new ResponseEntity(myProfileResponse, HttpStatus.OK);
        }

        if(password == null && removePhoto == null){
            userRepository.editNameEmail(name, email, principal.getName());
        }
        else if(!(password == null) && removePhoto == null){
            userRepository.editNameEmailPassword(name, email, BCrypt.hashpw(password, BCrypt.gensalt(12)), principal.getName());
        }

        return new ResponseEntity(new ResultResponse(true), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity editProfile(MultipartFile file, String name, String email, String password, String removePhoto, Principal principal) throws Exception {

        MyProfileResponse myProfileResponse = new MyProfileResponse();
        myProfileResponse.setResult(true);

        System.out.println(myProfileResponse.isResult());

        if (!checkMyProfile(file, name, email, password, principal, myProfileResponse)){

            System.out.println(myProfileResponse.isResult());

            return new ResponseEntity(myProfileResponse, HttpStatus.OK);
        }

            String filePath = uploadPhoto(file, principal);
            userRepository.editPasswordPhoto(name, email, BCrypt.hashpw(password, BCrypt.gensalt(12)), principal.getName(), filePath);

        return new ResponseEntity(new ResultResponse(true), HttpStatus.OK);
    }

    private Boolean checkMyProfile (MultipartFile file, String name, String email, String password, Principal principal, MyProfileResponse myProfileResponse){

        if(!name.matches("[\\w]+")){
            myProfileResponse.setErrors("name");
            myProfileResponse.setResult(false);
        }
        if(!(file == null) && file.getSize() > 5242880){
            myProfileResponse.setErrors("photo");
            myProfileResponse.setResult(false);
        }
        if(!(email.equals(principal.getName())) && userRepository.findUserByEmail(email).equals(email)){
            myProfileResponse.setErrors("email");
            myProfileResponse.setResult(false);
        }
        if(!(password == null) && password.length() < 6){
            myProfileResponse.setErrors("password");
            myProfileResponse.setResult(false);
        }

        return myProfileResponse.isResult();
    }

}

