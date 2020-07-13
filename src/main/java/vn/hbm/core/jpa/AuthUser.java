package vn.hbm.core.jpa;

import vn.hbm.core.annotation.AntColumn;
import vn.hbm.core.annotation.AntTable;

import java.io.Serializable;
import java.sql.Timestamp;

@AntTable(name = "auth_user", key = "id")
public class AuthUser implements Serializable {

    private Long id;
    private String userName;
    private String familyName;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String gender;
    private String birthday;
    private String passwd;
    private Timestamp createdTime;
    private Integer status;
    private Integer userType;

    @AntColumn(name = "id", auto_increment = true, index = 0)
    public Long getId() {
        return id;
    }

    @AntColumn(name = "id", auto_increment = true, index = 0)
    public void setId(Long id) {
        this.id = id;
    }

    @AntColumn(name = "user_name", index = 1)
    public String getUserName() {
        return userName;
    }

    @AntColumn(name = "user_name", index = 1)
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @AntColumn(name = "family_name", index = 2)
    public String getFamilyName() {
        return familyName;
    }

    @AntColumn(name = "family_name", index = 2)
    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    @AntColumn(name = "full_name", index = 3)
    public String getFullName() {
        return fullName;
    }

    @AntColumn(name = "full_name", index = 3)
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @AntColumn(name = "email", index = 4)
    public String getEmail() {
        return email;
    }

    @AntColumn(name = "email", index = 4)
    public void setEmail(String email) {
        this.email = email;
    }

    @AntColumn(name = "phone_number", index = 5)
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @AntColumn(name = "phone_number", index = 5)
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @AntColumn(name = "gender", index = 6)
    public String getGender() {
        return gender;
    }

    @AntColumn(name = "gender", index = 6)
    public void setGender(String gender) {
        this.gender = gender;
    }

    @AntColumn(name = "birthday", index = 7)
    public String getBirthday() {
        return birthday;
    }

    @AntColumn(name = "birthday", index = 7)
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    @AntColumn(name = "passwd", index = 8)
    public String getPasswd() {
        return passwd;
    }

    @AntColumn(name = "passwd", index = 8)
    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    @AntColumn(name = "created_time", index = 9)
    public Timestamp getCreatedTime() {
        return createdTime;
    }

    @AntColumn(name = "created_time", index = 9)
    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    @AntColumn(name = "status", index = 10)
    public Integer getStatus() {
        return status;
    }

    @AntColumn(name = "status", index = 10)
    public void setStatus(Integer status) {
        this.status = status;
    }

    @AntColumn(name = "user_type", index = 11)
    public Integer getUserType() {
        return userType;
    }

    @AntColumn(name = "user_type", index = 11)
    public void setUserType(Integer userType) {
        this.userType = userType;
    }
}
