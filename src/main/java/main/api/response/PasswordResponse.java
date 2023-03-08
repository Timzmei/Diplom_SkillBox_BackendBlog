package main.api.response;


import com.fasterxml.jackson.annotation.JsonProperty;

public class PasswordResponse  {


    @JsonProperty("result")
    private final boolean result;

    @JsonProperty("error")
    private final PasswordResponseError passwordResponseError;

    public PasswordResponse() {
        this.result = setResult();
        this.passwordResponseError = setPasswordResponseError();
    }

    public boolean setResult() {
        return false;
    }

    public PasswordResponseError setPasswordResponseError() {
       PasswordResponseError passwordResponseError = new PasswordResponseError();
    return passwordResponseError;
    }
}

class PasswordResponseError{

    @JsonProperty("code")
    private final String code;

    @JsonProperty("password")
    private final String password;

    @JsonProperty("captcha")
    private final String captcha;

    public PasswordResponseError() {
        this.code = setCode();
        this.password = setPassword();
        this.captcha = setCaptcha();
    }

    public String setCode() {
          return "Ссылка для восстановления пароля устарела. <a href=\"/auth/restore\">Запросить ссылку снова</a>";
    }

    public String setPassword() {
        return "Пароль короче 6-ти символов";
    }

    public String setCaptcha() {
        return "Код с картинки введён неверно";
    }
}
