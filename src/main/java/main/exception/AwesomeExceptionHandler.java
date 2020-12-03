package main.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import main.api.response.ImageResponse;
import main.api.response.LoginResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AwesomeExceptionHandler extends ResponseEntityExceptionHandler {


    //Ошибка авторизации
    @ExceptionHandler({UsernameNotFoundException.class})
    public ResponseEntity<Object> handleError1(UsernameNotFoundException e) {

        return new ResponseEntity<>(new LoginResponse(false), HttpStatus.OK);

    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<Object> handleError3(AuthenticationException e) {

        return new ResponseEntity<>(new LoginResponse(false), HttpStatus.OK);

    }

    //CommonsMultipartResolver
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Object> handleError2(MaxUploadSizeExceededException e) {


        ImageResponse imageResponse = new ImageResponse();
        return new ResponseEntity<>(imageResponse, HttpStatus.BAD_REQUEST);

//        return new ResponseEntity<>(new AwesomeException("There is no such user"), HttpStatus.NOT_FOUND);

    }

    @Data
    @AllArgsConstructor
    private static class AwesomeException {
        private String message;
    }
}