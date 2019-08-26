package com.cdd.gsl.domain;

import java.util.Date;

public class SlidePositionInfoDomain {
    private Long id;

    private String slidePosition;

    private Integer status;

    private Date createTs;

    private Date updateTs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSlidePosition() {
        return slidePosition;
    }

    public void setSlidePosition(String slidePosition) {
        this.slidePosition = slidePosition == null ? null : slidePosition.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
    }

    public Date getUpdateTs() {
        return updateTs;
    }

    public void setUpdateTs(Date updateTs) {
        this.updateTs = updateTs;
    }
}