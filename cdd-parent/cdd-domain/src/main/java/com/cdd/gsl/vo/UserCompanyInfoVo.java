package com.cdd.gsl.vo;

public class UserCompanyInfoVo {

    private Long userId;

    private String phone;

    //用户类型 1 个人 2 经纪人 3 经纪人主管
    private Integer userType;

    //头像
    private String portrait;

    //是否属于该公司成员  0 待审核 1 同意 2 不同意 3 未邀请
    private Integer agree;

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getAgree() {
        return agree;
    }

    public void setAgree(Integer agree) {
        if(agree == null){
            this.agree = 3;
        }else{
            this.agree = agree;
        }

    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    @Override
    public String toString() {
        return "UserCompanyInfoVo{" +
                "userId=" + userId +
                ", phone='" + phone + '\'' +
                ", userType=" + userType +
                ", portrait=" + portrait +
                ", agree=" + agree +
                '}';
    }
}
