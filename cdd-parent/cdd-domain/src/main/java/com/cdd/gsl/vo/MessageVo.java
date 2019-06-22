package com.cdd.gsl.vo;

import java.util.Date;

public class MessageVo {
    private Long messageId;

    private String message;

    private Long userId;

    private String createTs;

    private Long entrustId;

    private Long houseId;

    private Integer isRead;

    private String messageType;

    private Long objId;

    private String type;

    private Long sendUserId;

    private Long reveiveUserId;

    private SingleUserInfoVo sendUser;

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCreateTs() {
        return createTs;
    }

    public void setCreateTs(String createTs) {
        this.createTs = createTs;
    }

    public Long getEntrustId() {
        return entrustId;
    }

    public void setEntrustId(Long entrustId) {
        this.entrustId = entrustId;
    }

    public Long getHouseId() {
        return houseId;
    }

    public void setHouseId(Long houseId) {
        this.houseId = houseId;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public Long getObjId() {
        return objId;
    }

    public void setObjId(Long objId) {
        this.objId = objId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(Long sendUserId) {
        this.sendUserId = sendUserId;
    }

    public Long getReveiveUserId() {
        return reveiveUserId;
    }

    public void setReveiveUserId(Long reveiveUserId) {
        this.reveiveUserId = reveiveUserId;
    }

    public SingleUserInfoVo getSendUser() {
        return sendUser;
    }

    public void setSendUser(SingleUserInfoVo sendUser) {
        this.sendUser = sendUser;
    }
}
