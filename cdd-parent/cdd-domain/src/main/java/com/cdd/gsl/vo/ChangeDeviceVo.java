package com.cdd.gsl.vo;

public class ChangeDeviceVo {
    //验证手机号
    private String phone;

    //短信验证码
    private String verfication;

    //登录的手机号
    private String loginPhone;

    //唯一标识的设备ID
    private String deviceId;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getVerfication() {
        return verfication;
    }

    public void setVerfication(String verfication) {
        this.verfication = verfication;
    }

    public String getLoginPhone() {
        return loginPhone;
    }

    public void setLoginPhone(String loginPhone) {
        this.loginPhone = loginPhone;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
