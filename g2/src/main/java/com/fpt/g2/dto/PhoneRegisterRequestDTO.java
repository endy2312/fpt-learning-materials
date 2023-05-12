package com.fpt.g2.dto;

import com.fpt.g2.constant.CommonConstant;

import java.util.Objects;

public class PhoneRegisterRequestDTO {
    private String fullName;
    private String phone;
    private String password;
    private String confirmPassword;
    private String registerType;
    private String verifyCode;

    public String getFullName() {
        return Objects.isNull(fullName) ? CommonConstant.EMPTY_STRING : fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return Objects.isNull(phone) ? CommonConstant.EMPTY_STRING : phone;
    }

    public void setPhone(String email) {
        this.phone = email;
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

    public String getRegisterType() {
        return Objects.isNull(registerType) ? CommonConstant.SIGNUP_EMAIL : registerType;
    }

    public void setRegisterType(String registerType) {
        this.registerType = registerType;
    }

    public String getVerifyCode() {
        return Objects.isNull(verifyCode) ? CommonConstant.EMPTY_STRING : verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getUsername() {
        return phone;
    }
}
