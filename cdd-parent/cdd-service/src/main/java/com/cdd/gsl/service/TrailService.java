package com.cdd.gsl.service;

import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.domain.TrailInfoDomain;

public interface TrailService {
    CommonResult addTrail(TrailInfoDomain trailInfoDomain);

    CommonResult findTrailList(Long houseId);
}
