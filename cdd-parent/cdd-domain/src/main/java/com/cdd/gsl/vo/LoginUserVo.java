package com.cdd.gsl.vo;

public class LoginUserVo {
    private String phone;

    private String password;

    private String verfication;//验证码

    private String deviceId; //设备唯一标识

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

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
