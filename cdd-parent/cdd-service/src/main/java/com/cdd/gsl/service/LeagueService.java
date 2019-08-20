package com.cdd.gsl.service;

import com.cdd.gsl.admin.LeagueAdminConditionVo;
import com.cdd.gsl.common.result.CommonResult;

public interface LeagueService {
    public CommonResult findLeagueList(LeagueAdminConditionVo leagueAdminConditionVo);
}
