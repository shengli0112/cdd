package com.cdd.gsl.vo;

public class TrailInfoVo {
    private Long trailId;

    private String username;

    private String trailInfo;

    private String createTs;

    private Long houseId;

    public Long getTrailId() {
        return trailId;
    }

    public void setTrailId(Long trailId) {
        this.trailId = trailId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTrailInfo() {
        return trailInfo;
    }

    public void setTrailInfo(String trailInfo) {
        this.trailInfo = trailInfo;
    }

    public String getCreateTs() {
        return createTs;
    }

    public void setCreateTs(String createTs) {
        this.createTs = createTs;
    }

    public Long getHouseId() {
        return houseId;
    }

    public void setHouseId(Long houseId) {
        this.houseId = houseId;
    }
}
