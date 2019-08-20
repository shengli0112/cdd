package com.cdd.gsl.service.impl;

import com.cdd.gsl.admin.LeagueAdminConditionVo;
import com.cdd.gsl.common.constants.CddConstant;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.dao.LeagueInfoDao;
import com.cdd.gsl.domain.LeagueInfoDomain;
import com.cdd.gsl.service.LeagueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeagueServiceImpl implements LeagueService {

    private Logger logger = LoggerFactory.getLogger(LeagueServiceImpl.class);

    @Autowired
    private LeagueInfoDao leagueInfoDao;
    @Override
    public CommonResult findLeagueList(LeagueAdminConditionVo leagueAdminConditionVo) {
        CommonResult commonResult = new CommonResult();
        try {
            List<LeagueInfoDomain> leagueInfoDomainList = leagueInfoDao.leagueInfoList(leagueAdminConditionVo);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("查询成功");
            commonResult.setData(leagueInfoDomainList);
        } catch (Exception e){
            logger.error("LeagueServiceImpl findLeagueList error");
            e.printStackTrace();
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("服务器异常");
        }
        return commonResult;
    }
}
