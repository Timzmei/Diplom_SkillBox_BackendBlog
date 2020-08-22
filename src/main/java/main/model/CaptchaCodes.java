package main.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "captcha_codes")
public class CaptchaCodes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private int id;

    @NotNull
    private Date time;

    @Column(columnDefinition = "tinytext")
    @NotNull
    private String code;

    @Column(name = "secret_code", columnDefinition = "tinytext")
    @NotNull
    private String secretCode;

}
