package com.cdd.gsl.vo;

public class ThirdUserVo {

    //昵称
    private String nickName;

    //weichat weibo qq
    private String service;

    //三方唯一标识
    private String uuid;

    //头像
    private String portrait;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    @Override
    public String toString() {
        return "ThirdUserVo{" +
                "nickName=" + nickName +
                ", service='" + service + '\'' +
                ", uuid=" + uuid +
                ", portrait=" + portrait +
                '}';
    }
}
