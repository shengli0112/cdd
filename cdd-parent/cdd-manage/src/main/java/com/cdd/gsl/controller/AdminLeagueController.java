package com.cdd.gsl.controller;

import com.alibaba.fastjson.JSONObject;
import com.cdd.gsl.admin.LeagueAdminConditionVo;
import com.cdd.gsl.admin.NewsAdminConditionVo;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.domain.NewsInfoDomain;
import com.cdd.gsl.service.LeagueService;
import com.cdd.gsl.service.NewsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 加盟信息
 */
@RestController
@RequestMapping("league")
public class AdminLeagueController {

    private Logger logger = LoggerFactory.getLogger(AdminLeagueController.class);

    @Autowired
    private LeagueService leagueService;


    /**
     * 加盟信息列表
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findLeagueList")
    public CommonResult findLeagueList(LeagueAdminConditionVo leagueAdminConditionVo) throws Exception {
        logger.info("AdminNewsController findLeagueList start");
        return leagueService.findLeagueList(leagueAdminConditionVo);
    }


}
