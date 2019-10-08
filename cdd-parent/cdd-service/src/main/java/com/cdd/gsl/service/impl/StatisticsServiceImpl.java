package com.cdd.gsl.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cdd.gsl.admin.AdminMonthStatisticsVo;
import com.cdd.gsl.common.constants.CddConstant;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.common.util.DateUtil;
import com.cdd.gsl.dao.*;
import com.cdd.gsl.domain.ApplyBrokerInfoDomain;
import com.cdd.gsl.domain.ApplyBrokerInfoDomainExample;
import com.cdd.gsl.service.StatisticsService;
import com.cdd.gsl.vo.*;
import org.apache.logging.log4j.util.Strings;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    private Logger logger = LoggerFactory.getLogger(StatisticsServiceImpl.class);

    @Autowired
    private ApplyBrokerInfoDomainMapper applyBrokerInfoDomainMapper;

    @Autowired
    private ApplyBrokerInfoDao applyBrokerInfoDao;

    @Autowired
    private HouseInfoDao houseInfoDao;

    @Autowired
    private TrailInfoDao trailInfoDao;

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private EntrustInfoDao entrustInfoDao;

    @Override
    public CommonResult companyUser(Long userId) {
        CommonResult commonResult = new CommonResult();
        if(userId != null){
            List<UserInfoDemainVo> userList = applyBrokerInfoDao.companyHaveUserList(userId);
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
        if(statisticsContionVo.getLoginUserId() != null){
            List<Long> userIdList = new ArrayList<>();
            if(statisticsContionVo.getUserId()== null){
                List<UserInfoDemainVo> userList = applyBrokerInfoDao.companyHaveUserList(statisticsContionVo.getLoginUserId());
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

                }else if(statisticsContionVo.getTime().equals(CddConstant.STATISTICS_SEVENDAY)){
                    String fromDate = DateUtil.getDate(-8);
                    String toDate = DateUtil.getDate(-1);
                    from = fromDate.split(" ")[0]+" 00:00:00";
                    to = toDate.split(" ")[0]+" 23:59:59";

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

            List<CountHouse> countHouseList = houseInfoDao.countHouseByUserId(userIdList,statisticsContionVo.getFrom(),statisticsContionVo.getTo());
//            logger.info("");
            List<CountCoustomer> countCoustomerList = houseInfoDao.countCoustomerByUserId(userIdList,statisticsContionVo.getFrom(),statisticsContionVo.getTo());

            List<CountHouseTrail> countHouseTrailList = trailInfoDao.countHouseTrailByUserId(userIdList,statisticsContionVo.getFrom(),statisticsContionVo.getTo());

            List<CountCoustomerTrail> countCoustomerTrailList = trailInfoDao.countCoustomerTrailByUserId(userIdList,statisticsContionVo.getFrom(),statisticsContionVo.getTo());

            List<StatisticsInfoVo> statisticsInfoVoList = new ArrayList<>();
            if(!CollectionUtils.isEmpty(userIdList)){
                for(Long userId:userIdList){
                    StatisticsInfoVo statisticsInfoVo = new StatisticsInfoVo();
                    SingleUserInfoVo singleUserInfoVo = userInfoDao.findUserInfoById(userId);
                    statisticsInfoVo.setUsername(singleUserInfoVo.getUsername());
                    if(!CollectionUtils.isEmpty(countHouseList)){
                        countHouseList.forEach(countHouse ->{
                            if(countHouse.getUserId().longValue() == userId.longValue()){
                                statisticsInfoVo.setAddHouseCount(countHouse.getCountHouse());
                            }
                        });
                    }

                    if(!CollectionUtils.isEmpty(countCoustomerList)){
                        countCoustomerList.forEach(countCoustomer ->{
                            if(countCoustomer.getUserId().longValue() == userId.longValue()){
                                statisticsInfoVo.setAddCustomerCount(countCoustomer.getCountAddCoustomer());
                            }
                        });
                    }

                    if(!CollectionUtils.isEmpty(countHouseTrailList)){
                        countHouseTrailList.forEach(countHouseTrail ->{
                            if(countHouseTrail.getUserId().longValue() == userId.longValue()){
                                statisticsInfoVo.setTrailHouseCount(countHouseTrail.getCountHouseTrail());
                            }
                        });
                    }

                    if(!CollectionUtils.isEmpty(countCoustomerTrailList)){
                        countCoustomerTrailList.forEach(countCoustomerTrail ->{
                            if(countCoustomerTrail.getUserId().longValue() == userId.longValue()){
                                statisticsInfoVo.setTrailCustomerCount(countCoustomerTrail.getCountCoustomerTrail());
                            }
                        });
                    }

                    statisticsInfoVoList.add(statisticsInfoVo);
                    if(Strings.isNotEmpty(houseOrder)){
                        Collections.sort(statisticsInfoVoList, new Comparator<StatisticsInfoVo>() {
                            @Override
                            public int compare(StatisticsInfoVo o1, StatisticsInfoVo o2) {
                                return o2.getAddHouseCount() - o1.getAddHouseCount();
                            }
                        });
                    }

                    if(Strings.isNotEmpty(customerOrder)){
                        Collections.sort(statisticsInfoVoList, new Comparator<StatisticsInfoVo>() {
                            @Override
                            public int compare(StatisticsInfoVo o1, StatisticsInfoVo o2) {
                                return o2.getAddCustomerCount() - o1.getAddCustomerCount();
                            }
                        });
                    }

                    if(Strings.isNotEmpty(houseTrailOrder)){
                        Collections.sort(statisticsInfoVoList, new Comparator<StatisticsInfoVo>() {
                            @Override
                            public int compare(StatisticsInfoVo o1, StatisticsInfoVo o2) {
                                return o2.getTrailHouseCount() - o1.getTrailHouseCount();
                            }
                        });
                    }

                    if(Strings.isNotEmpty(customerTrailOrder)){
                        Collections.sort(statisticsInfoVoList, new Comparator<StatisticsInfoVo>() {
                            @Override
                            public int compare(StatisticsInfoVo o1, StatisticsInfoVo o2) {
                                return o2.getTrailCustomerCount() - o1.getTrailCustomerCount();
                            }
                        });
                    }

                }
            }
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("查询成功");
            commonResult.setData(statisticsInfoVoList);

        } else {
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("参数异常");
        }
        return commonResult;
    }

    @Override
    public CommonResult monthStatistics() {

        CommonResult commonResult = new CommonResult();
        //厂房
        List<AdminMonthStatisticsVo> cfTypeList = houseInfoDao.countHouseDateCountByHouseType(1);
        //仓库
        List<AdminMonthStatisticsVo> ckTypeList = houseInfoDao.countHouseDateCountByHouseType(2);
        //土地
        List<AdminMonthStatisticsVo> tdTypeList = houseInfoDao.countHouseDateCountByHouseType(3);
        JSONObject data = new JSONObject();
        JSONObject changfang = new JSONObject();
        JSONObject cangku = new JSONObject();
        JSONObject tudi = new JSONObject();
        if(!CollectionUtils.isEmpty(cfTypeList)){
            Set<String> monthSet = new HashSet<>();
            for(AdminMonthStatisticsVo month:cfTypeList){
                monthSet.add(month.getMonth());
            }
            //求租
            List<AdminMonthStatisticsVo> qzCfList = new ArrayList<>();
            //求购
            List<AdminMonthStatisticsVo> qgCfList = new ArrayList<>();
            //出租
            List<AdminMonthStatisticsVo> czCfList = new ArrayList<>();
            //出售
            List<AdminMonthStatisticsVo> csCfList = new ArrayList<>();

            for(AdminMonthStatisticsVo qcCount:cfTypeList){
                if(qcCount.getHouseUseType().equals("求租")){
                    qzCfList.add(qcCount);
                }else if(qcCount.getHouseUseType().equals("求购")){
                    qgCfList.add(qcCount);
                }else if(qcCount.getHouseUseType().equals("出租")){
                    czCfList.add(qcCount);
                }else if(qcCount.getHouseUseType().equals("出售")){
                    csCfList.add(qcCount);
                }
            }
            //求租数量
            List<Integer> qzCountList = new ArrayList<>();
            //求购数量
            List<Integer> qgCountList = new ArrayList<>();
            //出租数量
            List<Integer> czCountList = new ArrayList<>();
            //出售数量
            List<Integer> csCountList = new ArrayList<>();

            for(String month:monthSet){
                for(AdminMonthStatisticsVo qz:qzCfList){
                    if(month.equals(qz.getMonth())){
                        qzCountList.add(qz.getCount());
                    }else{
                        qzCountList.add(0);
                    }
                }

                for(AdminMonthStatisticsVo qg:qgCfList){
                    if(month.equals(qg.getMonth())){
                        qgCountList.add(qg.getCount());
                    }else{
                        qgCountList.add(0);
                    }
                }

                for(AdminMonthStatisticsVo cz:czCfList){
                    if(month.equals(cz.getMonth())){
                        czCountList.add(cz.getCount());
                    }else{
                        czCountList.add(0);
                    }
                }

                for(AdminMonthStatisticsVo cs:csCfList){
                    if(month.equals(cs.getMonth())){
                        csCountList.add(cs.getCount());
                    }else{
                        csCountList.add(0);
                    }
                }
            }
            changfang.put("month",monthSet);
            changfang.put("求租",qzCountList);
            changfang.put("求购",qgCountList);
            changfang.put("出租",czCountList);
            changfang.put("出售",csCountList);
        }

        if(!CollectionUtils.isEmpty(ckTypeList)){
            Set<String> monthSet = new HashSet<>();
            for(AdminMonthStatisticsVo month:ckTypeList){
                monthSet.add(month.getMonth());
            }
            //求租
            List<AdminMonthStatisticsVo> qzCkList = new ArrayList<>();
            //求购
            List<AdminMonthStatisticsVo> qgCkList = new ArrayList<>();
            //出租
            List<AdminMonthStatisticsVo> czCkList = new ArrayList<>();
            //出售
            List<AdminMonthStatisticsVo> csCkList = new ArrayList<>();

            for(AdminMonthStatisticsVo qcCount:ckTypeList){
                if(qcCount.getHouseUseType().equals("求租")){
                    qzCkList.add(qcCount);
                }else if(qcCount.getHouseUseType().equals("求购")){
                    qgCkList.add(qcCount);
                }else if(qcCount.getHouseUseType().equals("出租")){
                    czCkList.add(qcCount);
                }else if(qcCount.getHouseUseType().equals("出售")){
                    csCkList.add(qcCount);
                }
            }
            //求租数量
            List<Integer> qzCountList = new ArrayList<>();
            //求购数量
            List<Integer> qgCountList = new ArrayList<>();
            //出租数量
            List<Integer> czCountList = new ArrayList<>();
            //出售数量
            List<Integer> csCountList = new ArrayList<>();

            for(String month:monthSet){
                for(AdminMonthStatisticsVo qz:qzCkList){
                    if(month.equals(qz.getMonth())){
                        qzCountList.add(qz.getCount());
                    }else{
                        qzCountList.add(0);
                    }
                }

                for(AdminMonthStatisticsVo qg:qgCkList){
                    if(month.equals(qg.getMonth())){
                        qgCountList.add(qg.getCount());
                    }else{
                        qgCountList.add(0);
                    }
                }

                for(AdminMonthStatisticsVo cz:czCkList){
                    if(month.equals(cz.getMonth())){
                        czCountList.add(cz.getCount());
                    }else{
                        czCountList.add(0);
                    }
                }

                for(AdminMonthStatisticsVo cs:csCkList){
                    if(month.equals(cs.getMonth())){
                        csCountList.add(cs.getCount());
                    }else{
                        csCountList.add(0);
                    }
                }
            }
            cangku.put("month",monthSet);
            cangku.put("求租",qzCountList);
            cangku.put("求购",qgCountList);
            cangku.put("出租",czCountList);
            cangku.put("出售",csCountList);
        }

        if(!CollectionUtils.isEmpty(tdTypeList)){
            Set<String> monthSet = new HashSet<>();
            for(AdminMonthStatisticsVo month:tdTypeList){
                monthSet.add(month.getMonth());
            }
            //求租
            List<AdminMonthStatisticsVo> qzTdList = new ArrayList<>();
            //求购
            List<AdminMonthStatisticsVo> qgTdList = new ArrayList<>();
            //出租
            List<AdminMonthStatisticsVo> czTdList = new ArrayList<>();
            //出售
            List<AdminMonthStatisticsVo> csTdList = new ArrayList<>();

            for(AdminMonthStatisticsVo qcCount:ckTypeList){
                if(qcCount.getHouseUseType().equals("求租")){
                    qzTdList.add(qcCount);
                }else if(qcCount.getHouseUseType().equals("求购")){
                    qgTdList.add(qcCount);
                }else if(qcCount.getHouseUseType().equals("出租")){
                    czTdList.add(qcCount);
                }else if(qcCount.getHouseUseType().equals("出售")){
                    csTdList.add(qcCount);
                }
            }
            //求租数量
            List<Integer> qzCountList = new ArrayList<>();
            //求购数量
            List<Integer> qgCountList = new ArrayList<>();
            //出租数量
            List<Integer> czCountList = new ArrayList<>();
            //出售数量
            List<Integer> csCountList = new ArrayList<>();

            for(String month:monthSet){
                for(AdminMonthStatisticsVo qz:qzTdList){
                    if(month.equals(qz.getMonth())){
                        qzCountList.add(qz.getCount());
                    }else{
                        qzCountList.add(0);
                    }
                }

                for(AdminMonthStatisticsVo qg:qgTdList){
                    if(month.equals(qg.getMonth())){
                        qgCountList.add(qg.getCount());
                    }else{
                        qgCountList.add(0);
                    }
                }

                for(AdminMonthStatisticsVo cz:czTdList){
                    if(month.equals(cz.getMonth())){
                        czCountList.add(cz.getCount());
                    }else{
                        czCountList.add(0);
                    }
                }

                for(AdminMonthStatisticsVo cs:csTdList){
                    if(month.equals(cs.getMonth())){
                        csCountList.add(cs.getCount());
                    }else{
                        csCountList.add(0);
                    }
                }
            }
            tudi.put("month",monthSet);
            tudi.put("求租",qzCountList);
            tudi.put("求购",qgCountList);
            tudi.put("出租",czCountList);
            tudi.put("出售",csCountList);
        }
        data.put("changfang",changfang);
        data.put("cangku",cangku);
        data.put("tudi",tudi);

        commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
        commonResult.setMessage("查询成功");
        commonResult.setData(data);
        return commonResult;

    }
}
