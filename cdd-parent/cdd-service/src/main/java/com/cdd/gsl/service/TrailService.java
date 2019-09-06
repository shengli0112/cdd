package com.cdd.gsl.service;

import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.domain.FollowTrailInfo;
import com.cdd.gsl.domain.TrailInfoDomain;
import org.springframework.web.bind.annotation.RequestBody;

public interface TrailService {
    CommonResult addTrail(TrailInfoDomain trailInfoDomain);

    CommonResult findTrailList(Long houseId);

    CommonResult addFollowTrail(FollowTrailInfo followTrailInfo);

    CommonResult findFollowTrailList(Long userId);
}
