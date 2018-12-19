package com.cdd.gsl.domain;

import java.util.Date;

public class InformLeaseHouseRecordDomain {
    private Long id;

    private Long userId;

    private Long leaseHouseId;

    private Date createTs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getLeaseHouseId() {
        return leaseHouseId;
    }

    public void setLeaseHouseId(Long leaseHouseId) {
        this.leaseHouseId = leaseHouseId;
    }

    public Date getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
    }
}