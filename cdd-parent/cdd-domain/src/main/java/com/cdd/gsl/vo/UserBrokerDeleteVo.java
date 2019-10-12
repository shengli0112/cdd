package com.cdd.gsl.vo;

public class UserBrokerDeleteVo {
    private Long loginUserId;

    private Long deleteUserId;

    private Long transferUserId;

    public Long getLoginUserId() {
        return loginUserId;
    }

    public void setLoginUserId(Long loginUserId) {
        this.loginUserId = loginUserId;
    }

    public Long getDeleteUserId() {
        return deleteUserId;
    }

    public void setDeleteUserId(Long deleteUserId) {
        this.deleteUserId = deleteUserId;
    }

    public Long getTransferUserId() {
        return transferUserId;
    }

    public void setTransferUserId(Long transferUserId) {
        this.transferUserId = transferUserId;
    }
}
