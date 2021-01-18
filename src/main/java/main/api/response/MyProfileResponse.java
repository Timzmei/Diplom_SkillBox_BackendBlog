package main.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MyProfileResponse {

    @JsonProperty("result")
    private boolean result;
    @JsonProperty("errors")
    private MyProfileResponseErrors errors = new MyProfileResponseErrors();

    public void setErrors(String typeError) {
//        MyProfileResponseErrors myProfileResponseErrors = new MyProfileResponseErrors();

        if(typeError.equals("email")){
            errors.setEmail("Этот e-mail уже зарегистрирован");
        }
        else if (typeError.equals("photo")){
            errors.setPhoto("Фото слишком большое, нужно не более 5 Мб");
        }
        else if (typeError.equals("name")){
            errors.setName("Имя указано неверно, Имя может содержать буквы латинского алфавита, цифры или знак подчеркивания");
        }
        else if (typeError.equals("password")){
            errors.setPassword("Пароль короче 6-ти символов");
        }

    }
}
@Data
class MyProfileResponseErrors
{
    @JsonProperty("email")
    private String email;
    @JsonProperty("photo")
    private String photo;
    @JsonProperty("name")
    private String name;
    @JsonProperty("password")
    private String password;

//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public void setPhoto(String photo) {
//        this.photo = photo;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
}
