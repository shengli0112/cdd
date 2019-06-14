package com.cdd.gsl.vo;

public class ChatMessageVo {
    private Long id;

    private Long sendUserId;

    private SingleUserInfoVo sendUser;

    private Long receiveUserId;

    private SingleUserInfoVo receiveUser;

    private Long objId;

    private String type;

    private String messageContent;

    private String createTs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SingleUserInfoVo getSendUser() {
        return sendUser;
    }

    public void setSendUser(SingleUserInfoVo sendUser) {
        this.sendUser = sendUser;
    }

    public SingleUserInfoVo getReceiveUser() {
        return receiveUser;
    }

    public void setReceiveUser(SingleUserInfoVo receiveUser) {
        this.receiveUser = receiveUser;
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

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getCreateTs() {
        return createTs;
    }

    public void setCreateTs(String createTs) {
        this.createTs = createTs;
    }

    public Long getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(Long sendUserId) {
        this.sendUserId = sendUserId;
    }

    public Long getReceiveUserId() {
        return receiveUserId;
    }

    public void setReceiveUserId(Long receiveUserId) {
        this.receiveUserId = receiveUserId;
    }
}
