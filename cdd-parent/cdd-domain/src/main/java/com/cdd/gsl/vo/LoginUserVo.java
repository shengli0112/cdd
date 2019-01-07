package com.cdd.gsl.vo;

public class LoginUserVo {
    private String phone;

    private String password;

    private String verfication;//验证码

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerfication() {
        return verfication;
    }

    public void setVerfication(String verfication) {
        this.verfication = verfication;
    }
}
