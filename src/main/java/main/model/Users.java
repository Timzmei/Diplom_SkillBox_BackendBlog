package main.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Users
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private int id;

    @Column(name = "is_moderator", columnDefinition = "tinyint")
    @NotNull
    private int isModerator;

    @Column(name = "reg_time", columnDefinition = "datetime")
    @NotNull
    private Date regTime;

    @Column(columnDefinition = "varchar(255)")
    @NotNull
    private String name;

    @Column(columnDefinition = "varchar(255)")
    @NotNull
    private String email;

    @Column(columnDefinition = "varchar(255)")
    @NotNull
    private String password;

    @Column(columnDefinition = "varchar(255)")
    private String code;

    @Column(columnDefinition = "text")
    private String photo;

    public int getId() {
        return id;
    }

    public int getIsModerator() {
        return isModerator;
    }

    public void setIsModerator(int isModerator) {
        this.isModerator = isModerator;
    }

    public Date getRegTime() {
        return regTime;
    }

    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
