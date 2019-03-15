package com.cdd.gsl.domain;

import java.util.Date;

public class TrailInfoDomain {
    private Long id;

    private String trailInfo;

    private Long userId;

    private Long houseId;

    private Date createTs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTrailInfo() {
        return trailInfo;
    }

    public void setTrailInfo(String trailInfo) {
        this.trailInfo = trailInfo == null ? null : trailInfo.trim();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getHouseId() {
        return houseId;
    }

    public void setHouseId(Long houseId) {
        this.houseId = houseId;
    }

    public Date getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
    }
}