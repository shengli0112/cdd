package com.cdd.gsl.service.impl;

import com.cdd.gsl.common.constants.CddConstant;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.common.util.DateUtil;
import com.cdd.gsl.dao.ApplyBrokerInfoDao;
import com.cdd.gsl.dao.ApplyBrokerInfoDomainMapper;
import com.cdd.gsl.dao.HouseInfoDao;
import com.cdd.gsl.dao.TrailInfoDao;
import com.cdd.gsl.service.StatisticsService;
import com.cdd.gsl.vo.*;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private ApplyBrokerInfoDomainMapper applyBrokerInfoDomainMapper;

    @Autowired
    private ApplyBrokerInfoDao applyBrokerInfoDao;

    @Autowired
    private HouseInfoDao houseInfoDao;

    @Autowired
    private TrailInfoDao trailInfoDao;

    @Override
    public CommonResult companyUser(String companyName) {
        CommonResult commonResult = new CommonResult();
        if(Strings.isNotEmpty(companyName)){
            List<UserInfoDemainVo> userList = applyBrokerInfoDao.companyHaveUserList(companyName);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("查询成功");
            commonResult.setData(userList);
        }else{
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数不能为空");
        }
        return commonResult;
    }

    @Override
    public CommonResult companyData(StatisticsContionVo statisticsContionVo) {
        CommonResult commonResult = new CommonResult();
        if(Strings.isNotEmpty(statisticsContionVo.getCompanyName())){
            List<Long> userIdList = new ArrayList<>();
            if(statisticsContionVo.getUserId().equals(0)){
                List<UserInfoDemainVo> userList = applyBrokerInfoDao.companyHaveUserList(statisticsContionVo.getCompanyName());
                userList.forEach(userInfoDemainVo -> {
                    userIdList.add(userInfoDemainVo.getId());
                });
            }else{
                userIdList.add(statisticsContionVo.getUserId());
            }

            if(Strings.isNotEmpty(statisticsContionVo.getTime())){
                String from = "";
                String to = "";
                if(statisticsContionVo.getTime().equals(CddConstant.STATISTICS_TODAY)){
                    to = DateUtil.getDate(0);
                    from = to.split(" ")[0]+" 00:00:00";
                    statisticsContionVo.setFrom(from);
                    statisticsContionVo.setTo(to);
                }else if(statisticsContionVo.getTime().equals(CddConstant.STATISTICS_YESTERDAY)){
                    String date = DateUtil.getDate(-1);
                    from = date.split(" ")[0]+" 00:00:00";
                    to = date.split(" ")[0]+" 23:59:59";

                }else if(statisticsContionVo.getTime().equals(CddConstant.STATISTICS_MONTH)){
                    from = DateUtil.getMonthStart(0);
                    to = DateUtil.getMonthEnd(0);
                }else if(statisticsContionVo.getTime().equals(CddConstant.STATISTICS_PREMONTH)){
                    from = DateUtil.getMonthStart(-1);
                    to = DateUtil.getMonthEnd(-1);
                }
                statisticsContionVo.setFrom(from);
                statisticsContionVo.setTo(to);
            }

            String houseOrder = "";
            String customerOrder = "";
            String houseTrailOrder = "";
            String customerTrailOrder = "";
            if(Strings.isNotEmpty(statisticsContionVo.getSequence())){
                if(statisticsContionVo.getSequence().equals(CddConstant.STATISTICS_ADD_HOUSE)){
                    houseOrder = statisticsContionVo.getSequence();
                }else if(statisticsContionVo.getSequence().equals(CddConstant.STATISTICS_ADD_CUSTOMER)){
                    customerOrder = statisticsContionVo.getSequence();
                }else if(statisticsContionVo.getSequence().equals(CddConstant.STATISTICS_TRAIL_HOUSE)){
                    houseTrailOrder = statisticsContionVo.getSequence();
                }else if(statisticsContionVo.getSequence().equals(CddConstant.STATISTICS_TRAIL_CUSTOMER)){
                    customerTrailOrder = statisticsContionVo.getSequence();
                }
            }

            List<CountHouse> countHouseList = houseInfoDao.countHouseByUserId(userIdList,houseOrder,statisticsContionVo.getFrom(),statisticsContionVo.getTo());

            List<CountCoustomer> countCoustomerList = houseInfoDao.countCoustomerByUserId(userIdList,customerOrder,statisticsContionVo.getFrom(),statisticsContionVo.getTo());

            List<CountHouseTrail> countHouseTrailList = trailInfoDao.countHouseTrailByUserId(userIdList,houseTrailOrder,statisticsContionVo.getFrom(),statisticsContionVo.getTo());

            List<CountCoustomerTrail> countCoustomerTrailList = trailInfoDao.countCoustomerTrailByUserId(userIdList,customerTrailOrder,statisticsContionVo.getFrom(),statisticsContionVo.getTo());

            List<StatisticsInfoVo> statisticsInfoVoList = new ArrayList<>();
//            if(){}

        }
        return null;
    }

}
