package main.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;


public class AuthCaptchaResponse {

    @JsonProperty("secret")
    private String secret;
    @JsonProperty("image")
    private String image;

//    public AuthCaptchaResponse(String secret, String image) {
//        setSecret(secret);
//        setImage(image);
//    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
