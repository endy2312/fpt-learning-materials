package com.fpt.g2.dto;

import com.fpt.g2.constant.CommonConstant;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor
public class EmailRegisterRequestDTO {
    private String fullName;
    private String email;
    private String password;
    private String confirmPassword;
    private String verifyCode;
    private String registerType;

    public String getFullName() {
        return Objects.isNull(fullName) ? CommonConstant.EMPTY_STRING : fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return Objects.isNull(email) ? CommonConstant.EMPTY_STRING : email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPassword() {
        return Objects.isNull(fullName) ? CommonConstant.EMPTY_STRING : password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return Objects.isNull(confirmPassword) ? CommonConstant.EMPTY_STRING : confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getVerifyCode() {
        return Objects.isNull(verifyCode) ? CommonConstant.EMPTY_STRING : verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getRegisterType() {
        return Objects.isNull(registerType) ? CommonConstant.SIGNUP_EMAIL : registerType;
    }

    public void setRegisterType(String registerType) {
        this.registerType = registerType;
    }

    public String getUsername() {
        return  email.split("@")[0];
    }
}
